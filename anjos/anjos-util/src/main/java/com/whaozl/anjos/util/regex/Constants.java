package com.whaozl.anjos.util.regex;

public enum Constants {
	//部分匹配
	Repeatword("叠字","([\\u4E00-\\u9FFF\\u3105-\\u3129\\u3105-\\u3129])\\1+"),
	
	//完全匹配
	IsRepeatword("叠字","^([\\u4E00-\\u9FFF\\u3105-\\u3129\\u3105-\\u3129])\\1+$");
	
	
	private String key;
	private String value;

	private Constants(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
