package com.express.back.dto;

public class OrderOneDTO {
	private String iLanguage="0";//语言
	
	private String[] cemskind=new String[]{};//服务方式value
	private String[] cemskindd=new String[]{};//服务方式
	
	private String csphone="";//发件人联系电话
	private String cspostcode="";//发件人邮编
	private String csemail="";//发件人电子邮件
	private String cssms="";//发件人短信联系号
	
	private String csender="";//发件人姓名——解析中是csSign
	private String csaddr="";//发件人详细地址
	private String csunitname="";//发件人单位名称
	private String csprovince="";//发件人所在省/州
	private String cscountry="";//发件人所在国家
	private String cscity="";//发件人所在城市
	
	private String csenderE="";//发件人姓名英文——解析中是csSignE
	private String csaddrE="";//发件人详细地址英文
	private String csunitnameE="";//单位名称英文
	private String csprovinceE="";
	private String cscountryE="";
	private String cscityE="";
	
	public OrderOneDTO(){
	}

	public String getiLanguage() {
		return iLanguage;
	}

	public void setiLanguage(String iLanguage) {
		this.iLanguage = iLanguage;
	}

	public String[] getCemskind() {
		return cemskind;
	}

	public void setCemskind(String[] cemskind) {
		this.cemskind = cemskind;
	}

	public String[] getCemskindd() {
		return cemskindd;
	}

	public void setCemskindd(String[] cemskindd) {
		this.cemskindd = cemskindd;
	}

	public String getCsphone() {
		return csphone;
	}

	public void setCsphone(String csphone) {
		this.csphone = csphone;
	}

	public String getCspostcode() {
		return cspostcode;
	}

	public void setCspostcode(String cspostcode) {
		this.cspostcode = cspostcode;
	}

	public String getCsemail() {
		return csemail;
	}

	public void setCsemail(String csemail) {
		this.csemail = csemail;
	}

	public String getCssms() {
		return cssms;
	}

	public void setCssms(String cssms) {
		this.cssms = cssms;
	}

	public String getCsender() {
		return csender;
	}

	public void setCsender(String csender) {
		this.csender = csender;
	}

	public String getCsaddr() {
		return csaddr;
	}

	public void setCsaddr(String csaddr) {
		this.csaddr = csaddr;
	}

	public String getCsunitname() {
		return csunitname;
	}

	public void setCsunitname(String csunitname) {
		this.csunitname = csunitname;
	}

	public String getCsprovince() {
		return csprovince;
	}

	public void setCsprovince(String csprovince) {
		this.csprovince = csprovince;
	}

	public String getCscountry() {
		return cscountry;
	}

	public void setCscountry(String cscountry) {
		this.cscountry = cscountry;
	}

	public String getCscity() {
		return cscity;
	}

	public void setCscity(String cscity) {
		this.cscity = cscity;
	}

	public String getCsenderE() {
		return csenderE;
	}

	public void setCsenderE(String csenderE) {
		this.csenderE = csenderE;
	}

	public String getCsaddrE() {
		return csaddrE;
	}

	public void setCsaddrE(String csaddrE) {
		this.csaddrE = csaddrE;
	}

	public String getCsunitnameE() {
		return csunitnameE;
	}

	public void setCsunitnameE(String csunitnameE) {
		this.csunitnameE = csunitnameE;
	}

	public String getCsprovinceE() {
		return csprovinceE;
	}

	public void setCsprovinceE(String csprovinceE) {
		this.csprovinceE = csprovinceE;
	}

	public String getCscountryE() {
		return cscountryE;
	}

	public void setCscountryE(String cscountryE) {
		this.cscountryE = cscountryE;
	}

	public String getCscityE() {
		return cscityE;
	}

	public void setCscityE(String cscityE) {
		this.cscityE = cscityE;
	}
}
