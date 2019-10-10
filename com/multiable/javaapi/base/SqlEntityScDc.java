package com.multiable.javaapi.base;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SqlEntityScDc implements ObjectSerializer, ObjectDeserializer {

	@Override
	public int getFastMatchToken() {
		return JSONToken.LBRACE;
	}

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			if (out.isEnabled(SerializerFeature.WriteNullListAsEmpty)) {
				out.write("{}");
			} else {
				out.writeNull();
			}
			return;
		}

		SqlEntity data = (SqlEntity) object;

		// XXX need change to out.writeFieldValue
		JSONObject json = new JSONObject();

		json.put("moduleType", data.getModuleType());
		json.put("mainTableName", data.getMainTableName());
		json.put("subTableNames", Util.toStr(data.getSubDatas().keySet()));

		json.put(data.getMainTableName(), JSON.toJSONString(data.getMainData()));
		for (Entry<String, SqlTable> entry : data.getSubDatas().entrySet()) {
			json.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
		}

		// System.out.println(json.toJSONString());

		out.write(json.toJSONString());
	}

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {

		JSONObject json = parser.parseObject();
		if (json == null) {
			return null;
		}

		String module = json.getString("moduleType");
		if (Util.isEmpty(module)) {
			return null;
		}
		SqlEntity data = new SqlEntity();
		data.setModuleType(module);
		String tname = json.getString("mainTableName");
		data.setMainTableName(tname);

		SqlTable table = JSON.parseObject(json.getString(tname), SqlTable.class);
		if (table == null) {
			System.out.println("can't change str to sqlEntiy for :" + module);
			return null;
		}
		data.setMainData(table);

		String subTableNames = json.getString("subTableNames");
		if (!Util.isEmpty(subTableNames)) {
			String[] subNames = subTableNames.split(";");
			for (String subname : subNames) {
				data.getSubDatas().put(subname, JSON.parseObject(json.getString(subname), SqlTable.class));
			}
		}

		@SuppressWarnings("unchecked")
		T result = (T) data;

		return result;
	}

}
