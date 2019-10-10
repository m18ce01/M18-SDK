package com.multiable.javaapi.base;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SqlTableScDc implements ObjectSerializer, ObjectDeserializer {

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

		SqlTable data = (SqlTable) object;

		// XXX need change to out.writeFieldValue
		JSONObject json = new JSONObject();
		json.put("name", data.getName());
		json.put("size", data.size());

		JSONArray fields = Util.genStruJson(data);
		json.put("fields", fields);

		JSONArray values = Util.genDataJson(data);
		json.put("values", values);
		out.write(json.toJSONString());
	}

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {

		JSONObject json = parser.parseObject();
		if (json == null) {
			return null;
		}

		try {
			JSONArray fieldArray = (JSONArray) json.get("fields");

			SqlTable data = Util.createTableFromJsonStru(fieldArray);

			data.setName(json.getString("name"));

			JSONArray valueArray = (JSONArray) json.get("values");

			Util.appendJsonValues(data, valueArray);

			@SuppressWarnings("unchecked")
			T result = (T) data;

			return result;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

}
