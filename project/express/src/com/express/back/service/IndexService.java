package com.express.back.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.express.back.dto.BackUserLoginData;
import com.express.back.dto.WelcomeDTO;
import com.express.back.exception.ExpressException;
import com.express.back.util.HttpClientUtil;

public class IndexService {

	public WelcomeDTO Welcome369(BackUserLoginData buld) {
		WelcomeDTO dto=new WelcomeDTO();
		if (buld==null) {
			return dto;
		}
		try{
			Map<String, String> param = new HashMap<String, String>();
	        param.put("w", "369express"); 
	        Map<String, String> headers=new HashMap<String, String>();
	        String url = "http://www.369express.com/cgi-bin/GInfo.dll?MemStart";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());
	        String html=client.httpGet(url, param, true);
	        Document doc = Jsoup.parse(html);
	        Element member=doc.select("#DivInfo").get(0);
	        Element acount=doc.select("#DivInfo").get(1);
	        if(member!=null){
	        	dto.setMemberInfo(member.html().substring(member.html().indexOf("</h1>")+5));
	        }
	        if(acount!=null){
	        	dto.setAccountInfo(acount.html().substring(acount.html().indexOf("</h1>")+5));
	        }
		}catch(ExpressException e){
			e.printStackTrace();
		}
		return dto;
	}
}
