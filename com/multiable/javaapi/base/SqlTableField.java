package com.multiable.javaapi.base;

import java.io.Serializable;

public class SqlTableField implements Serializable {

	private static final long serialVersionUID = 1L;

	// the field name for datasource
	protected String name;
	// the title

	private SqlClassType classType;
	protected String fieldClassName;

	private Class<?> fieldClass;

	public SqlTableField() {

	}

	public SqlTableField(String name, Class<?> classz) {
		this();
		this.name = name;
		setFieldClass(classz);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFieldClass(Class<?> classz) {

		this.fieldClass = classz;

		if (classz != null) {
			fieldClassName = Util.getClassName(classz);

			classType = Util.getClassType(classz);

		}
	}

	public String getName() {
		return name;
	}

	public Class<?> getFieldClass() {
		return fieldClass;
	}

	public String getFieldClassName() {
		if (fieldClassName == null && fieldClass != null) {
			fieldClassName = Util.getClassName(fieldClass);
		}
		return fieldClassName;
	}

	public void setFieldClassName(String fieldClassName) {
		this.fieldClassName = fieldClassName;
	}

	/**
	 * @return the classType
	 */
	public SqlClassType getClassType() {
		return classType;
	}

	/**
	 * @param classType
	 *             the classType to set
	 */
	public void setClassType(SqlClassType classType) {
		this.classType = classType;
	}

	public SqlTableField getCopy() {
		SqlTableField newField = new SqlTableField();
		newField.setName(name);

		newField.setFieldClass(fieldClass);
		newField.setFieldClassName(fieldClassName);
		newField.setClassType(classType);

		return newField;
	}

}
