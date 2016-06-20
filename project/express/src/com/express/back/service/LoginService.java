package com.express.back.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.express.back.dto.BackUserLoginData;
import com.express.back.exception.ExpressException;
import com.express.back.util.HttpClientUtil;

public class LoginService {

	public void login369(BackUserLoginData buld){
		try{
	        Map<String, String> param = new HashMap<String, String>();
	        param.put("w", "369express");
	        param.put("ac", buld.getUsername());
	        param.put("pwd", buld.getPassword());
	        Map<String, String> headers=new HashMap<String, String>();
	        String url = "http://www.369express.com/cgi-bin/GInfo.dll?Login";
	        HttpClientUtil client=new HttpClientUtil(headers);//创建客户端
	        client.setSessionIdFlag(true);//设置获取会话SessionId
	        String html=client.httpPost(url, param);
	        Document doc = Jsoup.parse(html);
	        Element ele=doc.select(".Titl").first();
	        if(ele!=null && "验证失败".equals(ele.text())){
	        	Element info=doc.select(".Mess").first();
	        	buld.setErrorInfo(info==null ? "369服务器连接失败" : info.text());
	        }else{//验证成功
	        	 String sessionId=client.getSessionId();
	        	 buld.setSessionId(sessionId);
	        }
		}catch(ExpressException e){
			e.toString();
		}
	}
	
}
