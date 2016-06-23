package com.whaozl.anjos.util.regex;

public enum RegEx {
	base_tag("","/([a-z0-9])*( )*"),//词性标签替换
	Space("空白字符,等同于[\\t\n\\x0B\\f\\r]","[\\s]+"),
	//.net版本url 正则  http://[0-9A-Za-z\:/\-_#\?\=\.&]+
	Url("网页地址","(?i)[http|https]+://[0-9A-Za-z:/[-]_#[?][=][.][&]]*"),//[a-zA-z]+://[^\\s]*
	Html("html源码","(?i)<[^>]+>"),//(?)表示不区分大小写
	Script("js脚本","(?i)<[/s]*?script[^>]*?>[/s/S]*?<[/s]*?//[/s]*?script[/s]*?>"),
	
	
	Chinese("汉字","([\\u4E00-\\u9FFF\\u3105-\\u3129\\u3040-\\u30FF\\u31F0-\\u31FF]+)"),//只要有汉字就行
	Sign("标点符号","[\\pP\\p{Punct}]"),
	ChineseOrSigin("汉字和标点符号", Chinese.value +"|" + Sign.value),
	NoChineseOrSigin("不是汉字和标点符号","[^\\u4E00-\\u9FFF\\u3105-\\u3129\\u3040-\\u30FF\\u31F0-\\u31FF\\pP\\p{Punct}a-zA-Z0-9〇&/_]"),
	
	
	PositiveInteger("正整数","(^\\+{0,1}[1-9]\\d*)"),//可以带+号，第一个数字不能为0
	NegativeInteger("负整数","(^-[1-9]\\d*)"),//必须带负号，第一个数字也不能为0
	Integer("整数","([+-]{0,1}0)" +"|"+ PositiveInteger.value+"|"+NegativeInteger.value),//由0，正整数和负整数组成的
	PositiveDecimal("正小数","(\\+{0,1}[0]\\.[0-9]*|\\+{0,1}[1-9]\\d*\\.\\d+)"),//可以考带+号，并考虑两种情况:(1)第一个数字为0，则小数点后面应该不为0; (2)第一个数字不为0，小数点后可以为任意数字（必须有数）
	NegativeDecimal("负小数","(^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d+)"),
	Decimal("小数","([+-]{0,1}\\d+\\.\\d*)"),
	PositiveReal("正实数", PositiveDecimal.value +"|" + PositiveInteger.value),
	NegativeReal("负实数", NegativeDecimal.value +"|" +NegativeInteger.value),
	Real("正整数", Integer.value + "|" + Decimal.value),
	
	;
	
	private String  key;
	private String value;

	private RegEx(String key, String value) {
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
