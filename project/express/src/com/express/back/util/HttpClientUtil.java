package com.express.back.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {

	private String TextEncoding = "gb2312";
	private String ResultEncoding="utf-8";//返回的网页结果编码
	private Map<String, String> headers;
	private String SessionId;
	private boolean SessionIdFlag;//首次是否需要获取Sessionid
	
	public HttpClientUtil(){
	}
	
	public HttpClientUtil(Map<String, String> headers){
		this.headers=headers;
	}
	
	/**
	 * 会话维持
	 * @param headers
	 * @param sessionId
	 */
	public HttpClientUtil(Map<String, String> headers, String sessionId) {
		this.headers = headers;
		this.SessionId = sessionId;
		this.headers.put("Cookie", this.SessionId);
		this.SessionIdFlag=false;//第二次请求不需要记录SessionId
	}

	/**
	 * 发送post请求
	 * 异常或者没拿到返回结果的情况下,result为""
	 */
	public String httpPost(String url, Map<String, String> param) {
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer sb = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			//设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			httpPost = new HttpPost(url);
			//设置各种头信息  
			if (this.headers != null && this.headers.size() > 0) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			//传入各种参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (param != null) {
				String key=null, value=null;
				for (Entry<String, String> set : param.entrySet()) {
					key = set.getKey();
					value = set.getValue() == null ? "" : set.getValue();
					nvps.add(new BasicNameValuePair(key, value));
					sb.append(" [" + key + "-" + value + "] ");
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, this.TextEncoding));
			//设置连接超时时间  
			HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), 6000);
			//设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(), 60000);
			//开始进行请求
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {//请求不成功
				System.out.println("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				return "error";
			}
			entity = response.getEntity();
			if (null != entity) {
				byte[] bytes = EntityUtils.toByteArray(entity);
				result = new String(bytes, this.TextEncoding);
			} else {
				result = "";
			}
			if(this.SessionIdFlag==true){
				//获取SessionId
				String cookieValue=response.getFirstHeader("Set-Cookie").getValue();
				this.SessionId=cookieValue.substring(0, cookieValue.indexOf(";"));
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
	
	
	/**
	 * 发送post请求
	 * 异常或者没拿到返回结果的情况下,result为""
	 */
	public String httpPost2(String url, Map<String, String[]> param) {
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		StringBuffer sb = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			//设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			httpPost = new HttpPost(url);
			//设置各种头信息  
			if (this.headers != null && this.headers.size() > 0) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			//传入各种参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (param != null) {
				String key=null;
				for (Entry<String, String[]> set : param.entrySet()) {
					key = set.getKey();
					if(set.getValue()==null){
						nvps.add(new BasicNameValuePair(key, ""));
						sb.append(" [" + key + "-" + "" + "] ");
					}else{
						for(String val : set.getValue()){
							val=val==null ? "" : val;
							nvps.add(new BasicNameValuePair(key, val));
							sb.append(" [" + key + "-" + val + "] ");
						}
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, this.TextEncoding));
			//设置连接超时时间  
			HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), 6000);
			//设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(), 60000);
			//开始进行请求
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {//请求不成功
				System.out.println("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				return "error";
			}
			entity = response.getEntity();
			if (null != entity) {
				byte[] bytes = EntityUtils.toByteArray(entity);
				result = new String(bytes, this.TextEncoding);
			} else {
				result = "";
			}
			if(this.SessionIdFlag==true){
				//获取SessionId
				String cookieValue=response.getFirstHeader("Set-Cookie").getValue();
				this.SessionId=cookieValue.substring(0, cookieValue.indexOf(";"));
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
	
	/**
	 * 发送post请求
	 * 异常或者没拿到返回结果的情况下,result为""
	 */
	public String httpPost3(String url, String  param) {
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		//StringBuffer sb = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			//设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			httpPost = new HttpPost(url);
			//设置各种头信息  
			if (this.headers != null && this.headers.size() > 0) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			//传入各种参数
/*			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (param != null) {
				for (Entry<String, String[]> set : param.entrySet()) {
					String key = set.getKey();
					if(set.getValue()==null){
						nvps.add(new BasicNameValuePair(key, ""));
						sb.append(" [" + key + "-" + "" + "] ");
					}else{
						for(String val : set.getValue()){
							val=val==null ? "" : val;
							nvps.add(new BasicNameValuePair(key, val));
							sb.append(" [" + key + "-" + val + "] ");
						}
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, this.TextEncoding));
			*/
			  // 构造最简单的字符串数据
		    StringEntity reqeustEntity = new StringEntity(param,this.TextEncoding);
		    // 设置类型
		    //reqEntity.setContentType("application/x-www-form-urlencoded");
		    // 设置请求的数据
		    httpPost.setEntity(reqeustEntity);
			//设置连接超时时间  
			HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), 6000);
			//设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(), 60000);
			//开始进行请求
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {//请求不成功
				System.out.println("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				return "error";
			}
			entity = response.getEntity();
			if (null != entity) {
				byte[] bytes = EntityUtils.toByteArray(entity);
				result = new String(bytes, this.TextEncoding);
			} else {
				result = "";
			}
			if(this.SessionIdFlag==true){
				//获取SessionId
				String cookieValue=response.getFirstHeader("Set-Cookie").getValue();
				this.SessionId=cookieValue.substring(0, cookieValue.indexOf(";"));
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			if (null != httpclient) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
	/**
	 * 文件上传请求
	 */
	public String httpPostFile(String url, Map<String, String> param, File file) {
		DefaultHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		String result = "";
		//StringBuffer sb = new StringBuffer();
		try {
			httpclient = new DefaultHttpClient();
			//设置cookie的兼容性---考虑是否需要
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			//httpclient.getParams().setParameter("http.protocol.content-charset",Charset.forName(this.TextEncoding));
			httpPost = new HttpPost(url);
			//设置各种头信息  
			if (this.headers != null && this.headers.size() > 0) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
	        FileBody fileBody=new FileBody(file);
	        //对请求的表单域进行填充
			//MultipartEntity reqeustEntity = new MultipartEntity();
	        MultipartEntity reqeustEntity =new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName(this.TextEncoding));
			if(param!=null){
				String key=null,value=null;
				StringBody body;
				for (Entry<String, String> set : param.entrySet()) {
					key = set.getKey();
					value=set.getValue()==null ? "" : set.getValue();
					body=new StringBody(value,Charset.forName(this.TextEncoding));
					reqeustEntity.addPart(key, body);
				}
			}
			reqeustEntity.addPart("file1", fileBody);//本案例中file1在后
		    // 设置类型
		    //reqeustEntity.setContentType("application/x-www-form-urlencoded");
		    // 设置请求的数据
		    httpPost.setEntity(reqeustEntity);
			//设置连接超时时间  
			HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), 6000);
			//设置读数据超时时间
			HttpConnectionParams.setSoTimeout(httpPost.getParams(), 60000);
			System.out.println("executing request " + httpclient.getRequestExecutor());
			//开始进行请求
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {//请求不成功
				System.out.println("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				return "error";
			}
			entity = response.getEntity();
			if (null != entity) {
				byte[] bytes = EntityUtils.toByteArray(entity);
				result = new String(bytes, this.TextEncoding);
			} else {
				result = "";
			}
			if(this.SessionIdFlag==true){
				//获取SessionId
				String cookieValue=response.getFirstHeader("Set-Cookie").getValue();
				this.SessionId=cookieValue.substring(0, cookieValue.indexOf(";"));
			}
		}catch(Exception e){
			e.toString();
		}
		return result;
	}
	

	/**
	 * 发送 get 请求
	 * param转换为url地址中的&
	 */
	public String httpGet(String url, Map<String, String> param, boolean flagUrlAdd) {
		String result = null;
		DefaultHttpClient httpclient = null;
		HttpGet httpGet = null;
		HttpResponse response=null;
		StringBuffer sb=new StringBuffer();
		try {
			//传入参数
			if(param !=null && param.size()>0){
				sb.append(flagUrlAdd==true ? "&" : "");//类似这种http://www.369express.com/cgi-bin/GInfo.dll?MemStart&w=369express
				Iterator<Entry<String,String>> it=param.entrySet().iterator();
				Entry<String,String> entry=null;
				String value=null;
				while(it.hasNext()){
					entry=it.next();
					value= param.get(entry.getKey())==null ? "" : entry.getValue();
					sb.append(entry.getKey()).append("=").append(value);
					if(it.hasNext()){
						sb.append("&");
					}
				}
			}
			httpclient=new DefaultHttpClient();
			System.out.println(url+sb.toString());
			httpGet=new HttpGet(url+sb.toString());
			//设置各种头信息  
			if (this.headers != null && this.headers.size() > 0) {
				for (Entry<String, String> entry : headers.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, this.TextEncoding);
			if(this.SessionIdFlag==true){
				//获取SessionId
				String cookieValue=response.getFirstHeader("Set-Cookie").getValue();
				this.SessionId=cookieValue.substring(0, cookieValue.indexOf(";"));
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		return result;
	}
	
	public String getTextEncoding() {
		return TextEncoding;
	}

	public void setTextEncoding(String textEncoding) {
		TextEncoding = textEncoding;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getSessionId() {
		return SessionId;
	}

	public void setSessionId(String sessionId) {
		SessionId = sessionId;
	}

	public boolean getSessionIdFlag() {
		return SessionIdFlag;
	}

	public void setSessionIdFlag(boolean sessionIdFlag) {
		SessionIdFlag = sessionIdFlag;
	}

	public String getResultEncoding() {
		return ResultEncoding;
	}

	public void setResultEncoding(String resultEncoding) {
		ResultEncoding = resultEncoding;
	}
	
}
