package com.express.back.service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.express.back.dto.BackUserLoginData;
import com.express.back.dto.OrderOneDTO;
import com.express.back.exception.ExpressException;
import com.express.back.util.HttpClientUtil;

public class OrderOneService {
	
	
	public String QueryNum(BackUserLoginData buld, String cnum){
		String result="";
		try{
			Map<String, String> param = new HashMap<String, String>();
	        param.put("w", "369express");
	        param.put("nver", "3");
	        param.put("cnum", cnum);
	        Map<String, String> headers=new HashMap<String, String>();
	        String url = "http://www.369express.com/cgi-bin/GInfo.dll?ajxEmsQueryNum";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());//创建客户端
	        String html=client.httpGet(url, param, true);
	        //Document doc= Jsoup.parse(html);
	        //result = doc.select("body").text();
	        result=html;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取order_one这个页面的基本信息
	 */
	public OrderOneDTO OrderOne(BackUserLoginData buld) {
		OrderOneDTO dto=new OrderOneDTO();
		if(buld==null){
			return dto;
		}
		try{
			Map<String, String> param = new HashMap<String, String>();
	        param.put("w", "369express"); 
	        Map<String, String> headers=new HashMap<String, String>();
	        String url ="http://www.369express.com/cgi-bin/GInfo.dll?RecPreInputSetForm";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());//创建客户端
	        String html=client.httpGet(url, param, true);
	        Document docc = Jsoup.parse(html);
	        dto.setiLanguage(docc.select("input[name=ilanguage]").first().val());
	        
	        String doc=html.substring(html.indexOf("</form></body></html><Script language=JavaScript>")+49);
	       
	        dto.setCsphone( doc.substring(doc.indexOf("var csPhone=")+13 , doc.indexOf("var csSSms")-4) );
	        dto.setCspostcode( doc.substring(doc.indexOf("var csPostCode=")+16, doc.indexOf("var csProvince")-4 ) );
	        dto.setCsemail( doc.substring(doc.indexOf("var csSEMail=")+14, doc.indexOf("var x_acEmsKind")-4 ) );
	        dto.setCssms( doc.substring(doc.indexOf("var csSSms=")+12, doc.indexOf("var csSEMail")-4 ) );
	        
	        dto.setCsender( doc.substring(doc.indexOf("var csSign=")+12, doc.indexOf("var csSignE")-4 ) );
	        dto.setCsaddr( doc.substring(doc.indexOf("var csAddr=")+12, doc.indexOf("var csAddrE")-4 ) );
	        dto.setCsunitname( doc.substring(doc.indexOf("var csUnitName=")+16, doc.indexOf("var csUnitNameE")-4 ) );
	        dto.setCsprovince( doc.substring(doc.indexOf("var csProvince=")+16, doc.indexOf("var csProvinceE")-4 ) );
	        dto.setCscountry( doc.substring(doc.indexOf("var csCountry=")+15, doc.indexOf("var csCountryE")-4 ) );
	        dto.setCscity( doc.substring(doc.indexOf("var csCity=")+12, doc.indexOf("var csCityE")-4 ) );
	        
	        dto.setCsenderE( doc.substring(doc.indexOf("var csSignE=")+13, doc.indexOf("var csPhone")-4 ) );
	        dto.setCsaddrE( doc.substring(doc.indexOf("var csAddrE=")+13, doc.indexOf("var csCity")-4 ) );
	        dto.setCsunitnameE( doc.substring(doc.indexOf("var csUnitNameE=")+17, doc.indexOf("var csAddr")-4 ) );
	        dto.setCsprovinceE( doc.substring(doc.indexOf("var csProvinceE=")+17, doc.indexOf("var csCountry")-4 ) );
	        dto.setCscountryE( doc.substring(doc.indexOf("var csCountryE=")+16, doc.indexOf("var csSign")-4 ) );
	        dto.setCscityE( doc.substring(doc.indexOf("var csCityE=")+13, doc.indexOf("var csPostCode")-4 ) );
	        
	        String[] cemskind=doc.substring(doc.indexOf("var x_acEmsKind  = new Array(\"")+30, doc.indexOf("var x_acEmsKindd = new Array(")-4).split("\",\"");
	        String []cemskindd=doc.substring(doc.indexOf("var x_acEmsKindd = new Array(\"")+30, doc.indexOf("var x_nEmsKind=")-4).split("\",\"");
	        dto.setCemskind(cemskind);
	        dto.setCemskindd(cemskindd);
		}catch(ExpressException e){
			e.toString();
		}
        return dto;
	}

	
/*	public String RecPreInputSet(BackUserLoginData buld, Map<String, String[]> param) {
		String result="";
		try{
	        Map<String, String> headers=new HashMap<String, String>();
	        String url ="http://www.369express.com/cgi-bin/GInfo.dll?RecPreInputSet";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());//创建客户端
	        String html=client.httpPost2(url, param);
	        Document doc= Jsoup.parse(html);
	        result = doc.select("body").text();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}*/
	
	public String RecPreInputSet(BackUserLoginData buld, String param) {
		String result="";
		try{
	        Map<String, String> headers=new HashMap<String, String>();
	        String url ="http://www.369express.com/cgi-bin/GInfo.dll?RecPreInputSet";
	        HttpClientUtil client=new HttpClientUtil(headers, buld.getSessionId());//创建客户端
	        String html=client.httpPost3(url, URLDecoder.decode(param,"utf-8") );
	        //Document doc= Jsoup.parse(html);
	        //result = doc.select("body").text();
	        result=html;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
