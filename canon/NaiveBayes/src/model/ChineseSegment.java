package model;

import java.io.IOException;
import jeasy.analysis.MMAnalyzer;

/**
 * 中文分词类
 * @author whaozl
 *
 */
public class ChineseSegment {
	
	
	/**
	 * 对文本str进行中文你分词
	 * @param str 需要分词的文本
	 * @param splitToken 用于词语分割的标记，如"|"
	 * @return 分词结束后的一段文本,词语以splitToken隔开
	 */
	public static String split(String str, String splitToken){
		String result = null;
		MMAnalyzer mmanalyzer = new MMAnalyzer();
		try{
			result = mmanalyzer.segment(str, splitToken);
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
}
