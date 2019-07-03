package com.multiable.javaapi.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean pass = true;
	// see WsRespLib + MessageLib
	// same as(WsError.java!)

	private MsgType type = MsgType.Info;

	private int id;
	private String key;
	private String info = "";
	private String info_desc = "";

	private String trace = "";

	private String exception = "";

	private String jsonStr = "";

	private List<MsgLocator> locators = new ArrayList<>();

	private boolean htmlMessage = false;

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo_desc() {
		return info_desc;
	}

	public void setInfo_desc(String info_desc) {
		this.info_desc = info_desc;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public void addLocators(MsgLocator ltor) {
		locators.add(ltor);
	}

	public List<MsgLocator> getLocators() {
		return locators;
	}

	public void setLocators(List<MsgLocator> locators) {
		this.locators = locators;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isHtmlMessage() {
		return htmlMessage;
	}

	public void setHtmlMessage(boolean htmlMessage) {
		this.htmlMessage = htmlMessage;
	}

}
