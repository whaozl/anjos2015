package util;

import java.util.Set;
import java.util.Vector;

public class StopWordsHandler {
	public static Set<String> stopWords;//Map<停用词, 停用词索引>
	
	static{
		stopWords=WhaozlReader.readFromTxtByMultipleSet("stopwords/中文停用词表.txt");
	}
	
	/**
	 * 判断word是否为停用词
	 */
	public static boolean IsStopWord(String word){
		boolean flag=false;
		if( stopWords.contains( word ) ){
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 将单个样本的所有词中去掉停用词
	 * 去掉空格
	 */
	public static String[] DropStopWord( String[] oldWords){
		Vector<String> v = new Vector<String>();//线程安全
		for( int i = 0 ; i < oldWords.length; i++){
			if( oldWords[i] ==null || oldWords[i].trim().length() <=0 || StopWordsHandler.IsStopWord( oldWords[i].trim() ) == true ) continue;
			v.add( oldWords[i].trim() );
		}
		String[] temp = new String[ v.size() ];
		v.toArray( temp);
		return temp;
	}
}
