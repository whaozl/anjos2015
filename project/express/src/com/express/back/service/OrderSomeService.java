package com.express.back.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.express.back.dto.BackUserLoginData;
import com.express.back.util.HttpClientUtil;

public class OrderSomeService {
	
	public String UploadModel(BackUserLoginData buld, File file){
		String result="";
		try{
			Map<String, String> param = new HashMap<String, String>();
	        param.put("w", "369express");
	        Map<String, String> headers=new HashMap<String, String>();
	        String url = "http://www.369express.com/cgi-bin/GInfo.dll?RecPreInputUpload";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());//创建客户端
	        String html=client.httpPostFile(url, param, file);
	        //Document doc= Jsoup.parse(html);
	        //result = doc.select("body").text();
	        result=html;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
