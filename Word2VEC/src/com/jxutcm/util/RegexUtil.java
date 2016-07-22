package com.jxutcm.util;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

//参考网址：http://blog.csdn.net/z69183787/article/details/12848685

public class RegexUtil {	
	
	/**
	 * 正则表达式是否匹配——注意：正则表达式中不能随便加空格
	 */
    private static boolean isMatch(String regex, String original){  
        if (original == null || original.trim().equals("")) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(original);//matcher.group();
        return matcher.matches();
    }
    
    /**
     * 检查是否为全数字
     * 如 00434 或 0.434 或 445 或 19:00
     */
    public static boolean isDigit( String original ){
    	return isMatch("^(\\-|\\+)?\\d+(\\.\\d+)?(\\：\\d+)?(\\:\\d+)?(\\d+)?$", original);
    }
    
    /**
     * 是否为正整数——可以带+号，第一个数字不能为0
     */
    public static boolean isPositiveInteger(String original) {
        return isMatch("^\\+{0,1}[1-9]\\d*", original);// \\+出现次数是1次或0次  [1-9]中的单个数字出现1次  \\d*数字出现0次或多次——不可能+嘛
    }
    
    /**
     * 是否为负整数——必须带负号，第一个数字也不能为0
     */
    public static boolean isNegativeInteger(String original) {  
        return isMatch("^-[1-9]\\d*", original);
    }
    
    /**
     * 是否为整数——由0，正整数和负整数组成的
     */
    public static boolean isInteger(String original) {  
        return isMatch("[+-]{0,1}0", original) || isPositiveInteger(original) || isNegativeInteger(original);//[+-]{0,1}0 表示+-可以出现0次或1次
    }
    
    /**
     * 是否为正小数——可以考带+号，并考虑两种情况:(1)第一个数字为0，则小数点后面应该不为0; (2)第一个数字不为0，小数点后可以为任意数字（必须有数）
     */
    public static boolean isPositiveDecimal(String original){  
        return isMatch("\\+{0,1}[0]\\.[0-9]*|\\+{0,1}[1-9]\\d*\\.\\d+", original);
    }
    
    /**
     * 是否为负小数——必须带负号，其余都同上
     */
    public static boolean isNegativeDecimal(String original){  
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d+", original);
    }
  
    /**
     * 是否为小数——可以带正负号，并且带小数点就行了，但是至少保证小数点有一边不为空，所以这里还是分左边不为空和右边不为空的情况
     */
    public static boolean isDecimal(String original){  
        return isMatch("[+-]{0,1}\\d+\\.\\d*", original);//这里\\d+表示出现1次或多次   \\d*表示出现0次或多次
    }
    
    /**
     * 是否为实数
     */
    public static boolean isReal(String original){  
        return isInteger(original) || isDecimal(original);
    }
    
    /**
     * 是否为非实数
     */
    public static boolean isNotReal(String original){
    	return !isReal(original);
    }
    
    /**
     * 是否为正实数
     */
    public static boolean isPositiveReal(String original){
    	return isPositiveDecimal(original) || isPositiveInteger(original);
    }
    
    /**
     * 是否为负实数
     */
    public static boolean isNegativeReal(String original){
    	return isNegativeDecimal(original) || isNegativeInteger(original);
    }
    
}  