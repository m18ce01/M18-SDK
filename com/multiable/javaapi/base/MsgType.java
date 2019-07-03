package com.multiable.javaapi.base;

public enum MsgType {

	Info("Info"), Error("Error"), Warning("Warning");
	private String value;

	private MsgType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
