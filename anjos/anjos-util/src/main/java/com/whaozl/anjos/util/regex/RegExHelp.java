package com.whaozl.anjos.util.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExHelp {
	
    /**
     * 判断给定的正则表达式,是否能【完全匹配】到
     */
    public static boolean isMatch(String regex, String str){  
        if (str == null || str.trim().equals("")) {
            return false;
        }  
        Pattern p = Pattern.compile(regex);  
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 判断给定的正则表达式,是否能【匹配】到
     * 只要找到一个就行
     */
    public static boolean match(String regex, String str){  
        if (str == null || str.trim().equals("")) {
            return false;
        }  
        Pattern p = Pattern.compile(regex);  
        Matcher m = p.matcher(str);
        return m.find();
    }
    
    /**
     * 根据指定的正则表达式，对字符串进行匹配
     * 找到所有【匹配】的部分——不返回()的组
     */
    public static List<String> matchs(String regex, String str){
    	if(str==null) return null;
    	List<String> matched = new ArrayList<String>();
    	Pattern p = Pattern.compile(regex);
       	Matcher m = p.matcher(str);
       	while(m.find()){
       		matched.add(m.group(0));
       	}
       	return matched;
    }
    
    /**
     * 根据指定的正则表达式，对字符串进行匹配
     * 找到【第1个匹配】的部分——返回匹配完整的字符串+各分组
     */
    public static List<String> matchsFirst(String regex, String str){
    	if(str==null) return null;
    	List<String> matched = new ArrayList<String>();
    	Pattern p = Pattern.compile(regex);
       	Matcher m = p.matcher(str);
       	if(m.find()){
       		matched.add(m.group(0).trim());
       		for(int i=1, count=m.groupCount(); i<=count; i++){
       			matched.add(m.group(i)==null ? null : m.group(i).trim());
       		}
       	}
       	return matched;
    }
    
    /**
     * 叠字单独处理
     * 发现叠字 替换成 单字
     */
    public static String repeatwordHandler(String str){
    	if(str==null) return null;
      	Pattern p = Pattern.compile(Constants.Repeatword.getValue());
       	Matcher m = p.matcher(str);
       	String result= new String(str);
       	while(m.find()){
       		//m.group(0)=棒棒  m.group(1) 棒
       		result=result.replace(m.group(0),m.group(1));//group()或group(0)表示匹配到的全部
       	}
    	return result;
    }
}
