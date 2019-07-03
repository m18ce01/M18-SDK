package com.multiable.javaapi.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlTable implements Iterable<Integer>, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The attribute key representing begin of file.
	 */
	public static final int BOF = 0;

	private String name = "";

	// 0
	protected Map<String, Object[]> objMap = new HashMap<>();
	// 1
	protected Map<String, int[]> intMap = new HashMap<>();
	// 2
	protected Map<String, long[]> longMap = new HashMap<>();
	// 3
	protected Map<String, double[]> doubleMap = new HashMap<>();
	// 4
	protected Map<String, String[]> stringMap = new HashMap<>();
	// 5
	protected Map<String, boolean[]> booleanMap = new HashMap<>();

	/**
	 * data size.
	 */
	protected int size;
	/**
	 * data capacity
	 */
	private int dataLength;

	protected final List<String> fieldsName;
	protected final Map<String, SqlTableField> fields;

	public SqlTable() {
		fieldsName = new ArrayList<>();
		fields = new HashMap<>();
	}

	public SqlTable(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Field CURD---begin
	public void addField(SqlTableField thisField) {
		if (thisField == null) {

			// TODO add log!
			Util.info(" Field is null.");

			return;
		}
		if (thisField.getName() == null || thisField.getName().isEmpty()) {
			// TODO add log!
			Util.info(" Field is null or empty.");

			return;
		}

		if (isFieldExist(thisField.getName())) {
			return;
		}
		fieldsName.add(thisField.getName());
		fields.put(thisField.getName(), thisField);

	}

	public void deleteField(String fieldName) {
		if (fieldName == null || fieldName.isEmpty()) {
			// TODO add log!
			Util.info(" Field is null or empty.");
			return;
		}
		if (!isFieldExist(fieldName)) {
			return;
		}

		fieldsName.remove(fieldName);
		fields.remove(fieldName);
	}

	public void deleteField(SqlTableField thisField) {
		if (thisField == null) {
			// TODO add log!
			Util.info(" Field is null.d");
			return;
		}
		deleteField(thisField.getName());
	}

	public SqlTableField getField(String fieldName) {
		if (fieldName == null || fieldName.isEmpty()) {
			// TODO add log!
			Util.info("Field is null.");
			return null;
		}
		return fields.get(fieldName);
	}

	public boolean isFieldExist(String fieldName) {
		return fields.containsKey(fieldName);
	}

	public int getFieldCount() {
		return fieldsName.size();
	}

	// pls do't call this, unless you need
	public List<SqlTableField> getFields() {
		List<SqlTableField> myFields = new ArrayList<>();
		for (String fieldName : fieldsName) {
			// field list should have order
			SqlTableField field = fields.get(fieldName);
			if (field != null) {
				myFields.add(field);
			}
		}
		return myFields;
	}

	// only for scan, pls do't modify!
	public List<String> getFieldsName() {
		return fieldsName;
	}

	// Field CURD---end

	public SqlTable genEmptyTable() {
		SqlTable e = new SqlTable();
		for (SqlTableField field : fields.values()) {
			e.addField(field.getCopy());
		}
		return e;
	}

	/**
	 * Field object is same!
	 */
	public SqlTable genEmptyCopy() {
		SqlTable e = new SqlTable();
		for (SqlTableField field : fields.values()) {
			e.addField(field);
		}
		return e;
	}

	public void clear() {
		objMap.clear();
		intMap.clear();
		longMap.clear();
		doubleMap.clear();
		stringMap.clear();
		booleanMap.clear();
		size = 0;
		dataLength = 0;
	}

	public int size() {
		return size;
	}

	public boolean deleteRows(Collection<Integer> c) {
		if (c == null || c.isEmpty()) {
			return true;
		}
		if (c.size() == 1) {
			int myrow = 0;
			for (Integer row : c) {
				myrow = row.intValue();
			}
			return deleteRow(myrow);
		}
		for (Integer row : c) {
			if (row <= 0 || row > size()) {
				return false;
			}
		}
		int modCount = 0;

		for (Object[] value : objMap.values()) {
			if (value == null) {
				continue;
			}
			int r = 1, w = 0;

			for (; r <= size(); r++) {
				if (!c.contains(r)) {
					value[w++] = value[r - 1];
				}
			}
			if (w < size()) {
				modCount = size() - w;
				for (int i = w; i < size; i++) {
					value[i] = null;
				}
			}

		}
		for (int[] value : intMap.values()) {
			if (value == null) {
				continue;
			}
			int r = 1, w = 0;

			for (; r <= size(); r++) {
				if (!c.contains(r)) {
					value[w++] = value[r - 1];
				}
			}
			if (w < size()) {
				modCount = size() - w;
				for (int i = w; i < size; i++) {
					value[i] = 0;
				}
			}
		}
		for (long[] value : longMap.values()) {
			if (value == null) {
				continue;
			}
			int r = 1, w = 0;

			for (; r <= size(); r++) {
				if (!c.contains(r)) {
					value[w++] = value[r - 1];
				}
			}
			if (w < size()) {
				modCount = size() - w;
				for (int i = w; i < size; i++) {
					value[i] = 0;
				}
			}
		}
		for (double[] value : doubleMap.values()) {
			if (value == null) {
				continue;
			}
			int r = 1, w = 0;

			for (; r <= size(); r++) {
				if (!c.contains(r)) {
					value[w++] = value[r - 1];
				}
			}
			if (w < size()) {
				modCount = size() - w;
				for (int i = w; i < size; i++) {
					value[i] = 0;
				}
			}
		}
		for (String[] value : stringMap.values()) {
			if (value == null) {
				continue;
			}
			int r = 1, w = 0;

			for (; r <= size(); r++) {
				if (!c.contains(r)) {
					value[w++] = value[r - 1];
				}
			}
			if (w < size()) {
				modCount = size() - w;
				for (int i = w; i < size; i++) {
					value[i] = "";
				}
			}
		}
		for (boolean[] value : booleanMap.values()) {
			if (value == null) {
				continue;
			}
			int r = 1, w = 0;

			for (; r <= size(); r++) {
				if (!c.contains(r)) {
					value[w++] = value[r - 1];
				}
			}
			if (w < size()) {
				modCount = size() - w;
				for (int i = w; i < size; i++) {
					value[i] = false;
				}
			}
		}

		// all data has't init!
		if (modCount == 0) {
			modCount = c.size();
		}

		size = size - modCount;
		return true;
	}

	public boolean deleteRow() {
		return deleteRow(size());
	}

	public void ensureCapacityInternal(int minCapacity) {

		// overflow-conscious code
		if (minCapacity - dataLength > 0) {
			int newCapacity = 0;
			if (minCapacity == 1 && dataLength == 0) {
				newCapacity = 2;
			} else {
				newCapacity = minCapacity * 3 / 2;
				if (newCapacity - minCapacity < 20) {
					newCapacity = minCapacity + 20;
				} else if (newCapacity - minCapacity > 32000) {
					newCapacity = minCapacity + 32000;
				} else if (newCapacity - minCapacity > 10000) {
					newCapacity = minCapacity + 10000;
				} else if (newCapacity - minCapacity > 5000) {
					newCapacity = minCapacity + 5000;
				}
			}

			Set<String> keys = new HashSet<>();
			keys.addAll(objMap.keySet());
			for (String key : keys) {

				Object[] value = objMap.get(key);
				if (value == null) {
					continue;
				}
				value = Arrays.copyOf(value, newCapacity);
				objMap.put(key, value);
			}
			keys.clear();
			keys.addAll(intMap.keySet());
			for (String key : keys) {
				int[] value = intMap.get(key);
				if (value == null) {
					continue;
				}
				value = Arrays.copyOf(value, newCapacity);
				intMap.put(key, value);
			}
			keys.clear();
			keys.addAll(longMap.keySet());
			for (String key : keys) {
				long[] value = longMap.get(key);
				if (value == null) {
					continue;
				}
				value = Arrays.copyOf(value, newCapacity);
				longMap.put(key, value);
			}
			keys.clear();
			keys.addAll(doubleMap.keySet());
			for (String key : keys) {
				double[] value = doubleMap.get(key);
				if (value == null) {
					continue;
				}
				value = Arrays.copyOf(value, newCapacity);
				doubleMap.put(key, value);
			}
			keys.clear();
			keys.addAll(stringMap.keySet());
			for (String key : keys) {
				String[] value = stringMap.get(key);
				if (value == null) {
					continue;
				}
				value = Arrays.copyOf(value, newCapacity);
				stringMap.put(key, value);
			}
			keys.clear();
			keys.addAll(booleanMap.keySet());
			for (String key : keys) {
				boolean[] value = booleanMap.get(key);
				if (value == null) {
					continue;
				}
				value = Arrays.copyOf(value, newCapacity);
				booleanMap.put(key, value);
			}
			dataLength = newCapacity;
		}
	}

	// do't work with index
	public boolean deleteRow(int rec) {
		if (rec <= 0 || rec > size()) {
			return false;
		}
		// the data in list will not remove, so do this if you must to delete..
		int index = rec - 1;
		int numMoved = size - index - 1;
		if (numMoved > 0) {

			for (Object[] value : objMap.values()) {
				if (value != null) {
					System.arraycopy(value, index + 1, value, index, numMoved);
				}
			}
			for (int[] value : intMap.values()) {
				if (value != null) {
					System.arraycopy(value, index + 1, value, index, numMoved);
				}
			}
			for (long[] value : longMap.values()) {
				if (value != null) {
					System.arraycopy(value, index + 1, value, index, numMoved);
				}
			}
			for (double[] value : doubleMap.values()) {
				if (value != null) {
					System.arraycopy(value, index + 1, value, index, numMoved);
				}
			}
			for (String[] value : stringMap.values()) {
				if (value != null) {
					System.arraycopy(value, index + 1, value, index, numMoved);
				}
			}
			for (boolean[] value : booleanMap.values()) {
				if (value != null) {
					System.arraycopy(value, index + 1, value, index, numMoved);
				}
			}
			// xxx: you need set the field as 'null' to make JVM.GC work well
		}

		size--;

		return true;

	}

	// do't work with index
	public synchronized int addRow() {

		return addRow(size() + 1);

	}

	public void addRows(int number) {
		for (int i = 0; i < number; i++) {
			addRow(size() + 1);
		}
	}

	// do't work with index
	public int addRow(int rec) {
		if (rec <= 0 || rec > size() + 1) {
			return -1;
		}
		boolean isLastRow = false;
		if (rec == size() + 1) {
			isLastRow = true;
		}

		size++;

		ensureCapacityInternal(size);

		int tarRow = rec;

		for (Object[] dataArray : objMap.values()) {
			if (dataArray == null) {
				continue;
			}
			if (!isLastRow) {
				System.arraycopy(dataArray, tarRow - 1, dataArray, tarRow, size - tarRow);
			}
			dataArray[tarRow - 1] = null;
		}
		for (int[] dataArray : intMap.values()) {
			if (dataArray == null) {
				continue;
			}
			if (!isLastRow) {
				System.arraycopy(dataArray, tarRow - 1, dataArray, tarRow, size - tarRow);
			}

			dataArray[tarRow - 1] = 0;
		}
		for (long[] dataArray : longMap.values()) {
			if (dataArray == null) {
				continue;
			}
			if (!isLastRow) {
				System.arraycopy(dataArray, tarRow - 1, dataArray, tarRow, size - tarRow);
			}

			dataArray[tarRow - 1] = 0;
		}
		for (double[] dataArray : doubleMap.values()) {
			if (dataArray == null) {
				continue;
			}
			if (!isLastRow) {
				System.arraycopy(dataArray, tarRow - 1, dataArray, tarRow, size - tarRow);
			}

			dataArray[tarRow - 1] = 0;
		}
		for (String[] dataArray : stringMap.values()) {
			if (dataArray == null) {
				continue;
			}
			if (!isLastRow) {
				System.arraycopy(dataArray, tarRow - 1, dataArray, tarRow, size - tarRow);
			}

			dataArray[tarRow - 1] = "";
		}
		for (boolean[] dataArray : booleanMap.values()) {
			if (dataArray == null) {
				continue;
			}
			if (!isLastRow) {
				System.arraycopy(dataArray, tarRow - 1, dataArray, tarRow, size - tarRow);
			}

			dataArray[tarRow - 1] = false;
		}
		return tarRow;

	}

	// do't work with index
	public int copyRow(int srcRow) {
		if (srcRow <= 0 || srcRow > size()) {
			return -1;
		}

		size++;

		ensureCapacityInternal(size);

		int tarRow = size();

		for (Object[] dataArray : objMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];
		}
		for (int[] dataArray : intMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];
		}
		for (long[] dataArray : longMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];
		}
		for (double[] dataArray : doubleMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];
		}
		for (String[] dataArray : stringMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];
		}
		for (boolean[] dataArray : booleanMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];
		}

		return tarRow;
	}

	// do't work with index
	public int coverRow(int srcRow, int tarRow) {
		if (srcRow <= 0 || srcRow > size()) {
			return -1;
		}
		if (tarRow <= 0 || tarRow > size()) {
			return -1;
		}

		for (Object[] dataArray : objMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];

			dataArray[srcRow - 1] = null;
		}
		for (int[] dataArray : intMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];

			dataArray[srcRow - 1] = 0;
		}
		for (long[] dataArray : longMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];

			dataArray[srcRow - 1] = 0;
		}
		for (double[] dataArray : doubleMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];

			dataArray[srcRow - 1] = 0;
		}
		for (String[] dataArray : stringMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];

			dataArray[srcRow - 1] = "";
		}
		for (boolean[] dataArray : booleanMap.values()) {
			dataArray[tarRow - 1] = dataArray[srcRow - 1];

			dataArray[srcRow - 1] = false;
		}

		return tarRow;

	}

	public String getValueStr(int rec, String fieldName) {
		if (fieldName == null) {
			Util.info(" Field is null.. ");
			return "";
		}
		SqlTableField myField = fields.get(fieldName);
		if (myField == null) {
			// add log
			Util.info(" Field is null.. = " + fieldName);
			return "";
		}

		if (rec <= 0 || rec > size()) {
			return "";
		}

		Class<?> fieldClazz = myField.getFieldClass();

		if (Util.isStringType(fieldClazz)) {
			String result = getString(rec, fieldName);
			if (result == null) {
				return "";
			}
			return result;
		} else if (Util.isDoubleType(fieldClazz)) {
			return String.valueOf(getDouble(rec, fieldName));
		} else if (Util.isIntegerClass(fieldClazz)) {
			return String.valueOf(getInteger(rec, fieldName));
		} else if (Util.isLongClass(fieldClazz)) {
			return String.valueOf(getLong(rec, fieldName));
		} else if (Util.isBooleanClass(fieldClazz)) {
			return String.valueOf(getBoolean(rec, fieldName));
		} else {
			return String.valueOf(getObject(rec, fieldName));
		}
	}

	public double getValueNum(int rec, String fieldName) {
		if (Util.isEmpty(fieldName)) {
			Util.info(" Field is null...");
			return 0.0;
		}

		SqlTableField myField = fields.get(fieldName);
		if (myField == null) {
			// add log

			Util.info(" Field is null... = " + fieldName);
			return 0.0;
		}

		if (rec <= 0 || rec > size()) {
			return 0.0;
		}

		Class<?> fieldClazz = myField.getFieldClass();

		if (Util.isStringType(fieldClazz)) {
			return Util.toDouble(getString(rec, fieldName));
		} else if (Util.isDoubleType(fieldClazz)) {
			return getDouble(rec, fieldName);
		} else if (Util.isIntegerClass(fieldClazz)) {
			return getInteger(rec, fieldName);
		} else if (Util.isLongClass(fieldClazz)) {
			return getLong(rec, fieldName);
		} else if (Util.isBooleanClass(fieldClazz)) {
			return (getBoolean(rec, fieldName) ? 1 : 0);
		} else {
			return 0.0;
		}

	}

	public Object getValue(int rec, String fieldName) {
		if (Util.isEmpty(fieldName)) {
			Util.info(" Field is null...");
			return 0.0;
		}

		SqlTableField myField = fields.get(fieldName);
		if (myField == null) {
			// add log
			Util.info(" Field is null = " + fieldName);
			return null;
		}

		if (rec <= 0 || rec > size()) {
			return null;
		}

		Class<?> fieldClazz = myField.getFieldClass();

		if (Util.isStringType(fieldClazz)) {
			return getString(rec, fieldName);
		} else if (Util.isDoubleType(fieldClazz)) {
			return getDouble(rec, fieldName);
		} else if (Util.isIntegerClass(fieldClazz)) {
			return getInteger(rec, fieldName);
		} else if (Util.isLongClass(fieldClazz)) {
			return getLong(rec, fieldName);
		} else if (Util.isBooleanClass(fieldClazz)) {
			return getBoolean(rec, fieldName);
		} else {
			return getObject(rec, fieldName);
		}

	}

	public String getString(int rec, String fieldName) {
		String[] datas = stringMap.get(fieldName);
		if (datas != null) {
			String value = datas[rec - 1];
			if (value == null) {
				return "";
			}
			return value;
		}
		return "";
	}

	public String[] getStringArray(String fieldName) {
		String[] datas = stringMap.get(fieldName);

		if (datas != null) {
			return Arrays.copyOf(datas, size);
		}
		return new String[size];
	}

	public double getDouble(int rec, String fieldName) {
		double[] datas = doubleMap.get(fieldName);
		if (datas != null) {
			return datas[rec - 1];
		}
		return 0.0;
	}

	public double[] getDoubleArray(String fieldName) {
		double[] datas = doubleMap.get(fieldName);
		if (datas != null) {
			return Arrays.copyOf(datas, size);
		}
		return new double[size];
	}

	public int getInteger(int rec, String fieldName) {
		int[] datas = intMap.get(fieldName);
		if (datas != null) {
			return datas[rec - 1];
		}
		// this is add by iran( for unkonw mysql.update int will change to bigint)
		return Long.valueOf(getLong(rec, fieldName)).intValue();
		// return 0

	}

	public int[] getIntegerArray(String fieldName) {
		int[] datas = intMap.get(fieldName);
		if (datas != null) {
			return Arrays.copyOf(datas, size);
		}
		return new int[size];

	}

	public long getLong(int rec, String fieldName) {
		long[] datas = longMap.get(fieldName);
		if (datas != null) {
			return datas[rec - 1];
		}
		return 0l;
	}

	public long[] getLongArray(String fieldName) {
		long[] datas = longMap.get(fieldName);
		if (datas != null) {
			return Arrays.copyOf(datas, size);
		}
		return new long[size];
	}

	public boolean getBoolean(int rec, String fieldName) {
		boolean[] datas = booleanMap.get(fieldName);
		if (datas != null) {
			return datas[rec - 1];
		}
		return false;
	}

	public boolean[] getBooleanArray(String fieldName) {
		boolean[] datas = booleanMap.get(fieldName);
		if (datas != null) {
			return Arrays.copyOf(datas, size);
		}
		return new boolean[size];
	}

	public Object getObject(int rec, String fieldName) {
		Object[] datas = objMap.get(fieldName);
		if (datas != null) {
			return datas[rec - 1];
		}
		return null;
	}

	public Object[] getObjectArray(String fieldName) {
		Object[] datas = objMap.get(fieldName);
		if (datas != null) {
			return Arrays.copyOf(datas, size);
		}
		return new Object[size];
	}

	public void setValue(int rec, String fieldName, Object value) {
		if (Util.isEmpty(fieldName)) {
			Util.info("Field is null....");
			return;
		}

		SqlTableField myField = fields.get(fieldName);
		if (myField == null) {
			// add log
			Util.info("Field is null.... = " + fieldName);
			return;
		}

		if (rec <= 0 || rec > size()) {
			// add log
			Util.info(" rec is out of range.");
			return;
		}

		Class<?> fieldClazz = myField.getFieldClass();
		if (Util.isStringType(fieldClazz)) {
			setString(rec, fieldName, String.valueOf(value));

		} else if (Util.isDoubleType(fieldClazz)) {
			setDouble(rec, fieldName, Util.toDouble(value));

		} else if (Util.isIntegerClass(fieldClazz)) {
			setInteger(rec, fieldName, Util.toInteger(value));

		} else if (Util.isLongClass(fieldClazz)) {
			setLong(rec, fieldName, Util.toLong(value));

		} else if (Util.isBooleanClass(fieldClazz)) {
			setBoolean(rec, fieldName, Util.toBoolean(value));

		} else if (fieldClazz == Date.class && !(value instanceof Date)) {
			Object dateValue = Util.toDate(value, Date.class);
			if (dateValue != null) {
				setObject(rec, fieldName, dateValue);
			}
		} else {
			setObject(rec, fieldName, value);
		}
	}

	public void setString(int rec, String fieldName, String value) {

		String[] datas = stringMap.get(fieldName);
		if (datas == null) {
			if (Util.isEmpty(value)) {
				return;
			}
			datas = new String[dataLength];
			stringMap.put(fieldName, datas);
		}
		datas[rec - 1] = value;
	}

	public void setInteger(int rec, String fieldName, int value) {

		int[] datas = intMap.get(fieldName);
		if (datas == null) {
			if (value == 0) {
				return;
			}
			datas = new int[dataLength];
			intMap.put(fieldName, datas);
		}
		datas[rec - 1] = value;
	}

	public void setDouble(int recno, String fieldName, double value) {

		double[] datas = doubleMap.get(fieldName);
		if (datas == null) {
			if (value == 0.0) {
				return;
			}
			datas = new double[dataLength];
			doubleMap.put(fieldName, datas);
		}
		datas[recno - 1] = value;
	}

	public void setLong(int recno, String fieldName, long value) {

		long[] datas = longMap.get(fieldName);
		if (datas == null) {
			if (value == 0l) {
				return;
			}
			datas = new long[dataLength];
			longMap.put(fieldName, datas);
		}
		datas[recno - 1] = value;
	}

	public void setBoolean(int recno, String fieldName, boolean value) {

		boolean[] datas = booleanMap.get(fieldName);
		if (datas == null) {
			if (value == false) {
				return;
			}
			datas = new boolean[dataLength];
			booleanMap.put(fieldName, datas);
		}
		datas[recno - 1] = value;
	}

	public void setObject(int recno, String fieldName, Object value) {

		Object[] datas = objMap.get(fieldName);
		if (datas == null) {
			if (value == null) {
				return;
			}
			datas = new Object[dataLength];
			objMap.put(fieldName, datas);
		}
		datas[recno - 1] = value;
	}

	// slow!
	public void sort(Integer[] sortIndex) {

		Set<String> keys = new HashSet<>();
		keys.addAll(objMap.keySet());
		for (String key : keys) {
			Object[] value = objMap.get(key);
			if (value == null) {
				continue;
			}
			Object[] newValue = new Object[dataLength];

			for (int i = 0; i < sortIndex.length; i++) {
				newValue[i] = value[sortIndex[i] - 1];
			}
			objMap.put(key, newValue);
			value = null;
		}
		keys.clear();
		keys.addAll(intMap.keySet());
		for (String key : keys) {
			int[] value = intMap.get(key);
			if (value == null) {
				continue;
			}
			int[] newValue = new int[dataLength];

			for (int i = 0; i < sortIndex.length; i++) {
				newValue[i] = value[sortIndex[i] - 1];
			}
			intMap.put(key, newValue);
			value = null;
		}
		keys.clear();
		keys.addAll(longMap.keySet());
		for (String key : keys) {
			long[] value = longMap.get(key);
			if (value == null) {
				continue;
			}
			long[] newValue = new long[dataLength];

			for (int i = 0; i < sortIndex.length; i++) {
				newValue[i] = value[sortIndex[i] - 1];
			}
			longMap.put(key, newValue);
			value = null;
		}
		keys.clear();
		keys.addAll(doubleMap.keySet());
		for (String key : keys) {
			double[] value = doubleMap.get(key);
			if (value == null) {
				continue;
			}
			double[] newValue = new double[dataLength];

			for (int i = 0; i < sortIndex.length; i++) {
				newValue[i] = value[sortIndex[i] - 1];
			}
			doubleMap.put(key, newValue);
			value = null;
		}
		keys.clear();
		keys.addAll(stringMap.keySet());
		for (String key : keys) {
			String[] value = stringMap.get(key);
			if (value == null) {
				continue;
			}
			String[] newValue = new String[dataLength];

			for (int i = 0; i < sortIndex.length; i++) {
				newValue[i] = value[sortIndex[i] - 1];
			}
			stringMap.put(key, newValue);
			value = null;
		}
		keys.clear();
		keys.addAll(booleanMap.keySet());
		for (String key : keys) {
			boolean[] value = booleanMap.get(key);
			if (value == null) {
				continue;
			}
			boolean[] newValue = new boolean[dataLength];

			for (int i = 0; i < sortIndex.length; i++) {
				newValue[i] = value[sortIndex[i] - 1];
			}
			booleanMap.put(key, newValue);
			value = null;
		}

	}

	@Override
	public Iterator<Integer> iterator() {
		SqlTableIterator si = new SqlTableIterator();
		si.setDataSize(size());
		return si;
	}

}
