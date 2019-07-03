package com.multiable.javaapi.base;

import java.io.Serializable;

public class MsgLocator implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tableName;

	private String colName = "";

	private long id;

	// row is only for footer table !
	// for headerTable the row should 0
	// if the table show in page as 'EditTable', the row should > 0
	private int row = 0;

	private LocatorType type = LocatorType.Field;

	private String locatorKey;

	private String tableMess = "";

	private String colMess = "";

	public MsgLocator() {

	}

	public MsgLocator(String tableName, String col) {
		this.tableName = tableName;
		this.colName = col;
	}

	public MsgLocator(String tableName, String col, int row) {
		this.tableName = tableName;
		this.colName = col;
		this.row = row;
	}

	public MsgLocator(String locatorKey) {
		this.locatorKey = locatorKey;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public LocatorType getType() {
		return type;
	}

	public void setType(LocatorType type) {
		this.type = type;
	}

	public String getLocatorKey() {
		if (locatorKey == null || locatorKey.isEmpty()) {
			int row = getRow();
			StringBuilder locBuilder = new StringBuilder(tableName);
			if (row > 0) {
				locBuilder.append(".").append(colName).append(".").append(row);
			} else {
				if (!colName.isEmpty()) {
					locBuilder.append(".").append(colName);
				}
			}
			return locBuilder.toString();
		}
		return locatorKey;
	}

	public void setLocatorKey(String locatorKey) {
		this.locatorKey = locatorKey;
	}

	public String getTableMess() {
		return tableMess;
	}

	public void setTableMess(String tableMess) {
		this.tableMess = tableMess;
	}

	public String getColMess() {
		return colMess;
	}

	public void setColMess(String colMess) {
		this.colMess = colMess;
	}

}
