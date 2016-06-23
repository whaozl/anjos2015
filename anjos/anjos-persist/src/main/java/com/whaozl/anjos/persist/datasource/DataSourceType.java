package com.whaozl.anjos.persist.datasource;

public enum DataSourceType {
	LKKData("数据");
	
	private String value;

	private DataSourceType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
