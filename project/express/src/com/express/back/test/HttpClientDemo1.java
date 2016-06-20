package com.express.back.test;

import java.util.HashMap;
import java.util.Map;

import com.express.back.util.HttpClientUtil;

public class HttpClientDemo1 {

	public static void main(String[] args) {
		
        Map<String, String> param = new HashMap<String, String>();
        param.put("w", "369express");
        param.put("ac", "max");
        param.put("pwd", "123456");
        
        Map<String, String> headers=new HashMap<String, String>();
		
		HttpClientUtil client=new HttpClientUtil(headers);
		
		client.setSessionIdFlag(true);//设置获取会话SessionId
		
		String url = "http://www.369express.com/cgi-bin/GInfo.dll?Login";
		
		String html=client.httpPost(url, param);
		
		String SessionId=client.getSessionId();
		
		System.out.println(html);
		
		HttpClientUtil client2=new HttpClientUtil(headers, SessionId);//一定要用SessionId初始化
		
    	String result2=client2.httpGet("http://www.369express.com/cgi-bin/GInfo.dll?RecPreInputList&w=369express&cnum=&cdes=&bdate=&edate=&cemskind=&nper=10&nn=87&np=1", null, false);
    	
    	System.out.println(result2);
    	
    	//String result3=client2.httpGet("http://www.369express.com/cgi-bin/GInfo.dll?EmsPrintTab&w=369express&cnum=RJ564002847CN", null);
    	
		//http://www.369express.com/cgi-bin/GInfo.dll?RecPreInputSetForm&w=369express
    	
    	//System.out.println(result3);
		
	}
	

}
