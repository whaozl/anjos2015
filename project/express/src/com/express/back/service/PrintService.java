package com.express.back.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.express.back.dto.BackUserLoginData;
import com.express.back.util.HttpClientUtil;

public class PrintService {
	
	public String EmsPrintTab(BackUserLoginData buld, String cnum) {
		String result="";
		try{
	        Map<String, String> param = new HashMap<String, String>();
	        param.put("w", "369express");
	        param.put("cnum", cnum);
	        Map<String, String> headers=new HashMap<String, String>();
	        String url ="http://www.369express.com/cgi-bin/GInfo.dll?EmsPrintTab";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());
	        String html=client.httpGet(url, param, true);
	        Document doc= Jsoup.parse(html);
	        Elements eles=doc.select("img");
	        for(Element ele : eles){
	        	ele.attr("src", "http://www.369express.com"+ele.attr("src").toString() );
	        }
	        result=doc.html();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
