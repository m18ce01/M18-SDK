package com.multiable.javaapi.base;

public class EntityResult extends CheckResult {

	private static final long serialVersionUID = 1L;

	private SqlEntity entity;

	public SqlEntity getEntity() {
		return entity;
	}

	public void setEntity(SqlEntity entity) {
		this.entity = entity;
	}

}
