package com.multiable.javaapi.base;

public enum SqlClassType {

	// String
	Str(0),

	// boolean
	Boolean(3),

	// Number
	Num(10),

	// Date
	Date(20),

	// DateTime;
	DateTime(21),

	// Image
	Img(90),

	// Other
	Other(99);

	private int value;

	SqlClassType(int i) {
		this.value = i;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	public static SqlClassType getType(String value) {
		if (value != null) {
			switch (value) {
			case "Str":
				return SqlClassType.Str;

			case "Boolean":
				return SqlClassType.Boolean;

			case "Num":
				return SqlClassType.Num;

			case "Date":
				return SqlClassType.Date;

			case "DateTime":
				return SqlClassType.DateTime;

			case "Img":
				return SqlClassType.Img;

			default:
				break;
			}

		}

		return SqlClassType.Other;
	}

	public static SqlClassType getSqlClassType(int value) {
		for (SqlClassType type : SqlClassType.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return Str;
	}
}
