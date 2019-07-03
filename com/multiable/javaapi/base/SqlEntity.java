package com.multiable.javaapi.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// only for (create insert update)
public class SqlEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// with module.xml
	private String moduleType = "";

	private String mainTableName = "";

	private SqlTable mainData = null;

	private Map<String, SqlTable> subDatas = new HashMap<>();

	public SqlTable getMainData() {
		return mainData;
	}

	public SqlTable getData(String name) {
		if (mainTableName.equals(name)) {
			return mainData;
		}
		return subDatas.get(name);
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getMainTableName() {
		return mainTableName;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public void setMainData(SqlTable mainData) {
		this.mainData = mainData;
	}

	public Map<String, SqlTable> getSubDatas() {
		return subDatas;
	}

	public void setSubDatas(Map<String, SqlTable> subDatas) {
		this.subDatas = subDatas;
	}

}
