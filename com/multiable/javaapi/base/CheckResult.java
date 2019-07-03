package com.multiable.javaapi.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean pass = true;

	private long entityId;

	// only for pass=true
	private List<Integer> showInfos = new ArrayList<>();

	private List<CheckMsg> msgs = new ArrayList<>();

	public void appendCheckMsg(CheckMsg msg) {
		if (msg == null) {
			return;
		}
		if (pass && !msg.isPass()) {
			pass = false;
		}
		msgs.add(msg);
	}

	public void appendCheckResult(CheckResult info) {
		if (info == null) {
			return;
		}
		if (pass && !info.isPass()) {
			pass = false;
		}
		msgs.addAll(info.getMsgs());
		showInfos.addAll(info.getShowInfos());
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public List<Integer> getShowInfos() {
		return showInfos;
	}

	public void setShowInfos(List<Integer> showInfos) {
		this.showInfos = showInfos;
	}

	public List<CheckMsg> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<CheckMsg> msgs) {
		this.msgs = msgs;
	}

	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

}
