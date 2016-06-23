package com.whaozl.anjosi.util.dto;

public class StringDTO {
	private String value;
	
	public StringDTO(){
	}
	public StringDTO(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value==null ? "" : value;
	}
}
