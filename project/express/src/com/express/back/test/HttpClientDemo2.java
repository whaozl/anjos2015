package com.express.back.test;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.express.back.util.HttpClientUtil;

public class HttpClientDemo2 {

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
		
		System.out.println(html);
		
        Document doc = Jsoup.parse(html);
        Element ele=doc.select(".Titl").first();
        
        if(ele!=null){
        	System.out.println(ele.text());
        }
        
        if(ele!=null && "验证失败".equals(ele.text())){
        	Element info=doc.select(".Mess").first();
        	System.out.println(info==null ? "369服务器连接失败" : info.text());
        }
		
	}
	

}
