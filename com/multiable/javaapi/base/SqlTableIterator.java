package com.multiable.javaapi.base;

import java.util.Iterator;

public class SqlTableIterator implements Iterator<Integer> {

	private int index = 0;

	private int dataSize = 0;

	@Override
	public boolean hasNext() {
		if (index >= dataSize) {
			return false;
		}
		return true;

	}

	@Override
	public Integer next() {
		if (index >= dataSize) {
			return dataSize;
		}
		index = index + 1;
		return index;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

}
