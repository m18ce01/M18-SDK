package com.multiable.javaapi.base;

import java.awt.Image;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

public class Util {

	public static void init() {
		SerializeConfig.getGlobalInstance().put(SqlEntity.class, new SqlEntityScDc());
		ParserConfig.getGlobalInstance().putDeserializer(SqlEntity.class, new SqlEntityScDc());

		SerializeConfig.getGlobalInstance().put(SqlTable.class, new SqlTableScDc());
		ParserConfig.getGlobalInstance().putDeserializer(SqlTable.class, new SqlTableScDc());
	}

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

	public static <A extends Iterable<String>> String toStr(A data) {
		if (data == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (String info : data) {
			if (first) {
				first = false;
			} else {
				sb.append(";");
			}
			sb.append(info);

		}

		return sb.toString();
	}

	public static JSONArray genStruJson(SqlTable data) {
		if (data == null) {
			return null;
		}

		JSONArray fields = new JSONArray();
		for (SqlTableField field : data.getFields()) {
			JSONObject jsonField = new JSONObject(16, true);
			jsonField.put("name", field.getName());
			jsonField.put("classType", field.getClassType().getValue());
			jsonField.put("fieldClassName", field.getFieldClassName());
			jsonField.put("fieldClass", field.getFieldClass().getName());
			fields.add(jsonField);
		}

		return fields;

	}

	public static JSONArray genDataJson(SqlTable data) {
		return genDataJson(data, false);
	}

	public static JSONArray genDataJson(SqlTable data, boolean includeEmpty) {
		if (data == null) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		for (int i = 1; i <= data.size(); i++) {
			JSONObject json = new JSONObject(16, true);
			for (SqlTableField field : data.getFields()) {

				Class<?> clazz = field.getFieldClass();
				if (isStringType(clazz)) {
					String sVal = data.getString(i, field.getName());
					if (!isEmpty(sVal) || includeEmpty) {
						json.put(field.getName(), sVal);
					}

				} else if (isDoubleType(clazz)) {
					double dVal = data.getDouble(i, field.getName());
					if (dVal != 0.0 || includeEmpty) {
						json.put(field.getName(), dVal);
					}
				} else if (isIntegerClass(clazz)) {
					int iVal = data.getInteger(i, field.getName());
					if (iVal != 0 || includeEmpty) {
						json.put(field.getName(), iVal);
					}
				} else if (isLongClass(clazz)) {
					long lVal = data.getLong(i, field.getName());
					if (lVal != 0l || includeEmpty) {
						json.put(field.getName(), lVal);
					}
				} else if (isBooleanClass(clazz)) {
					boolean bVal = data.getBoolean(i, field.getName());
					if (bVal || includeEmpty) {
						json.put(field.getName(), bVal);
					}

				} else {
					Object fieldValue = data.getValue(i, field.getName());
					if (fieldValue instanceof Date) {
						// if(DateLib.isEmptyDate(date))
						json.put(field.getName(), date2Str((Date) fieldValue, "yyyy-MM-dd HH:mm:ss"));
					} else {
						if (fieldValue != null) {
							json.put(field.getName(), fieldValue);
						}
					}
				}

			}
			jsonArray.add(json);
		}
		return jsonArray;

	}

	public static SqlTable createTableFromJsonStru(JSONArray fieldArray) {
		if (fieldArray == null) {
			return null;
		}
		SqlTable data = new SqlTable();

		for (int i = 0; i < fieldArray.size(); i++) {
			JSONObject jsonField = (JSONObject) fieldArray.get(i);
			SqlTableField sqlTableField = new SqlTableField();
			sqlTableField.setName(jsonField.getString("name"));

			sqlTableField.setClassType(SqlClassType.getSqlClassType(Integer.parseInt(jsonField.getString("classType"))));
			try {
				// ClassUtils.forName(jsonField.getString("fieldClass")) .classForPrimitive(typeStr)

				sqlTableField.setFieldClass(getClass(jsonField.getString("fieldClass")));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			data.addField(sqlTableField);
		}

		return data;
	}

	public static void appendJsonValues(SqlTable data, JSONArray valueArray) {
		appendJsonValues(data, valueArray, false);
	}

	public static void appendJsonValues(SqlTable data, JSONArray valueArray, boolean skipEmptyVal) {
		if (data == null || valueArray == null) {
			return;
		}

		data.addRows(valueArray.size());

		for (int i = 0; i < valueArray.size(); i++) {
			JSONObject jsonValue = (JSONObject) valueArray.get(i);
			setJsonValToTable(data, jsonValue, skipEmptyVal, i + 1);
		}

	}

	public static void setJsonValToTable(SqlTable data, JSONObject jsonValue, boolean skipEmptyVal, int index) {
		if (data == null || jsonValue == null || index <= 0) {
			return;
		}

		for (SqlTableField field : data.getFields()) {
			if (skipEmptyVal && !jsonValue.containsKey(field.getName())) {
				continue;
			}

			Object value = jsonValue.get(field.getName());
			Class<?> clazz = field.getFieldClass();
			if (isStringType(clazz)) {
				data.setString(index, field.getName(), String.valueOf(value));
			} else if (isDoubleType(clazz)) {
				data.setDouble(index, field.getName(), toDouble(value));
			} else if (isIntegerClass(clazz)) {
				data.setInteger(index, field.getName(), toInteger(value));
			} else if (isLongClass(clazz)) {
				data.setLong(index, field.getName(), toLong(value));
			} else if (isBooleanClass(clazz)) {
				data.setBoolean(index, field.getName(), toBoolean(value));
			} else {

				Object objectValue = null;
				// date
				if (clazz.equals(java.util.Date.class) || clazz.equals(java.sql.Date.class) || clazz.equals(java.sql.Timestamp.class)
						|| clazz.equals(java.sql.Time.class)) {
					if (value != null) {
						objectValue = createDateTime(String.valueOf(value));
					}
				} else if (clazz.equals(java.lang.Byte[].class)) {
					continue;
				} else if (clazz.equals(Clob.class)) {
					continue;
				} else if (clazz.equals(Blob.class)) {
					continue;
				} else {
					objectValue = value;

				}

				data.setObject(index, field.getName(), objectValue);
			}
		}
	}

	public static String date2Str(Date date, String dateFormat) {
		if (date == null) {
			return "";// TODO 1900-01-01?
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static Class getClass(String className) throws ClassNotFoundException {
		return getClass(className, true);
	}

	public static Class getClass(String className, boolean initialize) throws ClassNotFoundException {
		ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
		ClassLoader loader = contextCL == null ? Util.class.getClassLoader() : contextCL;
		return getClass(loader, className, initialize);
	}

	public static final char PACKAGE_SEPARATOR_CHAR = '.';
	public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
	private static final Map<String, String> abbreviationMap = new HashMap<>();
	static {
		abbreviationMap.put("int", "I");
		abbreviationMap.put("boolean", "Z");
		abbreviationMap.put("float", "F");
		abbreviationMap.put("long", "J");
		abbreviationMap.put("short", "S");
		abbreviationMap.put("byte", "B");
		abbreviationMap.put("double", "D");
		abbreviationMap.put("char", "C");
	}

	public static Class getClass(ClassLoader classLoader, String className, boolean initialize) throws ClassNotFoundException {
		try {
			Class clazz;
			if (abbreviationMap.containsKey(className)) {
				String clsName = "[" + abbreviationMap.get(className);
				clazz = Class.forName(clsName, initialize, classLoader).getComponentType();
			} else {
				clazz = Class.forName(toCanonicalName(className), initialize, classLoader);
			}
			return clazz;
		} catch (ClassNotFoundException ex) {
			// allow path separators (.) as inner class name separators
			int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);

			if (lastDotIndex != -1) {
				try {
					return getClass(classLoader, className.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR_CHAR + className.substring(lastDotIndex + 1),
							initialize);
				} catch (ClassNotFoundException ex2) {
				}
			}

			throw ex;
		}
	}

	private static String toCanonicalName(String className) {
		className = deleteWhitespace(className);
		if (className == null) {
			throw new NullPointerException("className");
		} else if (className.endsWith("[]")) {
			StringBuilder classNameBuffer = new StringBuilder();
			while (className.endsWith("[]")) {
				className = className.substring(0, className.length() - 2);
				classNameBuffer.append("[");
			}
			String abbreviation = abbreviationMap.get(className);
			if (abbreviation != null) {
				classNameBuffer.append(abbreviation);
			} else {
				classNameBuffer.append("L").append(className).append(";");
			}
			className = classNameBuffer.toString();
		}
		return className;
	}

	public static String deleteWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

}
