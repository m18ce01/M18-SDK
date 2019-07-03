package com.multiable.javaapi.base;

import java.awt.Image;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Util {

	public static void info(Object object) {

		System.out.println(String.valueOf(object));
		// getLoggerByTrace().info(String.valueOf(object));
	}

	public static String getClassName(Class<?> classz) {
		String className = "";
		if (classz != null) {
			className = getClassName(classz.getSimpleName());
		}
		return className;
	}

	public static String getClassName(String classPath) {
		String className = getLastName(classPath, ".");

		className = getLastName(className, "$");

		return className;
	}

	public static String getLastName(String name, String divide) {
		String lastName = name;
		if (!isEmpty(name) && !isEmpty(divide)) {
			lastName = lastName.substring(lastName.lastIndexOf(divide) + divide.length());
		}
		return lastName;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static SqlClassType getClassType(Class<?> classz) {

		if (classz != null) {
			if (isNumberType(classz)) {
				return SqlClassType.Num;
			} else if (isBooleanClass(classz)) {
				return SqlClassType.Boolean;
			} else if (isDateType(classz)) {
				return SqlClassType.DateTime;
			} else if (isImageType(classz)) {
				return SqlClassType.Img;
			} else if (isStringType(classz)) {
				return SqlClassType.Str;
			}
		}
		return SqlClassType.Other;
	}

	static boolean isNumberType(Class<?> clazz) {
		return Number.class.isAssignableFrom(clazz) || (clazz.isPrimitive()
				&& (clazz == Integer.TYPE || clazz == Float.TYPE || clazz == Double.TYPE || clazz == Long.TYPE || clazz == Short.TYPE));
	}

	static boolean isBooleanClass(Class<?> clazz) {
		return clazz == Boolean.class || clazz == Boolean.TYPE;
	}

	static boolean isDateType(Class<?> clazz) {
		return Date.class.isAssignableFrom(clazz);
	}

	static boolean isImageType(Class<?> clazz) {
		return Image.class.isAssignableFrom(clazz);
	}

	static boolean isStringType(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	static boolean isDoubleType(Class<?> clazz) {
		return isDoubleClass(clazz) || isFloatClass(clazz) || isBigDecimalClass(clazz);
	}

	static boolean isDoubleClass(Class<?> clazz) {
		return clazz == Double.class || clazz == Double.TYPE;
	}

	static boolean isLongClass(Class<?> clazz) {
		return clazz == Long.class || clazz == Long.TYPE;
	}

	static boolean isShortClass(Class<?> clazz) {
		return clazz == Short.class || clazz == Short.TYPE;
	}

	static boolean isFloatClass(Class<?> clazz) {
		return clazz == Float.class || clazz == Float.TYPE;
	}

	static boolean isBigDecimalClass(Class<?> clazz) {
		return clazz == BigDecimal.class;
	}

	static boolean isIntegerClass(Class<?> clazz) {
		return clazz == Integer.class || clazz == Integer.TYPE;
	}

	static boolean isByteClass(Class<?> clazz) {
		return clazz == Byte.class || clazz == Byte.TYPE;
	}

	static boolean isBigIntegerClass(Class<?> clazz) {
		return clazz == BigInteger.class;
	}

	static boolean isUtilDateClass(Class<?> clazz) {
		return Date.class == clazz;
	}

	static boolean isSqlDateClass(Class<?> clazz) {
		return java.sql.Date.class == clazz;
	}

	static boolean isSqlTimeClass(Class<?> clazz) {
		return java.sql.Time.class == clazz;
	}

	static boolean isSqlTimeStampClass(Class<?> clazz) {
		return Timestamp.class == clazz;
	}

	public static Double toDouble(Object value) {
		if (value == null) {
			return 0d;
		}
		if (value instanceof Number) {
			if (value instanceof Double) {
				return (Double) value;
			}
			return (Double) toNumber((Number) value, Double.class);

		}
		if (value instanceof String) {
			return Double.valueOf(value.toString());
		}
		return 0d;
	}

	public static Integer toInteger(Object value) {
		if (value == null) {
			return 0;
		}
		if (value instanceof Number) {
			if (value instanceof Integer) {
				return (Integer) value;
			}
			return (Integer) toNumber((Number) value, Integer.class);

		}
		String strValue = String.valueOf(value);
		if (strValue.isEmpty()) {
			return 0;
		}
		return Integer.valueOf(strValue);
	}

	private static Object toNumber(Number value, Class<?> clazz) {

		if (value == null) {
			return 0;
		}
		if (isDoubleClass(clazz)) {
			return Double.valueOf(value.doubleValue());
		}
		if (isByteClass(clazz)) {
			return Byte.valueOf(value.byteValue());
		}
		if (isShortClass(clazz)) {
			return Short.valueOf(value.shortValue());
		}
		if (isIntegerClass(clazz)) {
			return Integer.valueOf(value.intValue());
		}
		if (isLongClass(clazz)) {
			return Long.valueOf(value.longValue());
		}
		if (isFloatClass(clazz)) {
			return Float.valueOf(value.floatValue());
		}
		if (isBigIntegerClass(clazz)) {
			return BigInteger.valueOf(value.longValue());
		}
		if (isBigDecimalClass(clazz)) {
			return new BigDecimal(Double.toString(value.doubleValue()));
		}
		return 0;
	}

	public static Long toLong(Object value) {
		if (value == null) {
			return 0L;
		}
		if (value instanceof Number) {
			if (value instanceof Long) {
				return (Long) value;
			}
			return (Long) toNumber((Number) value, Long.class);
		}
		if (value instanceof String) {
			return Long.valueOf(value.toString());
		}
		return 0L;
	}

	private static Set<String> booleanSet = new HashSet<>();

	static {
		booleanSet.add("1");
		booleanSet.add("1.0");
		booleanSet.add("1.00");

		booleanSet.add("Y");
		booleanSet.add("YES");

		booleanSet.add("T");
		booleanSet.add("TRUE");

		// booleanSet.add("OK");
		// booleanSet.add("PASS");
	}

	public static boolean toBoolean(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue() == 1.0;
		}
		if (value instanceof String) {
			String upStr = value.toString().toUpperCase();

			if (!isEmpty(upStr)) {
				return booleanSet.contains(upStr);
			}
		}
		return false;
	}

	public static Object toDate(Object value, Class<?> clazz) {
		if (value == null) {
			return null;
		}
		if (clazz == null) {
			clazz = Date.class;
		}
		long time = 0L;
		if (value instanceof Date) {
			time = ((Date) value).getTime();
		} else if (value instanceof Number) {
			time = toLong(value);
		} else if (value instanceof String) {
			Date date = createDateTime(value.toString());
			if (date == null) {
				date = createDate(value.toString());
			}
			if (date != null) {
				time = date.getTime();
			}
		} else {
			// return 1970-01-01?
			return null;
		}

		if (isUtilDateClass(clazz)) {
			return new Date(time);

		} else if (isSqlTimeStampClass(clazz)) {
			return new java.sql.Timestamp(time);

		} else if (isSqlTimeClass(clazz)) {
			return new java.sql.Time(time);

		} else if (isSqlDateClass(clazz)) {
			return new java.sql.Date(time);
		}
		// return 1970-01-01?
		return null;
	}

	static Date createDateTime(String dateStr) {
		return createDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	static Date createDate(String dateStr) {
		return createDateTime(dateStr, "yyyy-MM-dd");
	}

	static Date createDateTime(String dateStr, String dateFormat) {
		if (dateStr == null || dateStr.isEmpty() || dateStr.trim().isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			info(" can't change str2date for :" + dateStr + "  format:" + dateFormat);
		}

		return date;
	}

}
