package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * io读操作公共类
 * @author whaozl
 *
 */
public class WhaozlReader {
	
	/**
	 * 从文本文件中读取
	 * 返回的是多行样本
	 * 字符串数组格式
	 */
	public static String[] readFromTxtByMultiple(String path) {
		List<String> list = new ArrayList<String>();
		FileReader fr = null;//文件文本流
		BufferedReader br = null;//缓冲文本流
		String line;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			while ( (line = br.readLine()) != null ) {
				if( !"".equals(line) ){
					list.add( line );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String[] array = new String[ list.size() ];
		list.toArray(array);
		return array;
	}
	
	/**
	 * 从文本文件中读取
	 * 返回的是多行样本
	 * Map格式
	 */
	public static Set<String> readFromTxtByMultipleSet(String path) {
		Set<String> set = new HashSet<String>();
		FileReader fr = null;//文件文本流
		BufferedReader br = null;//缓冲文本流
		String line;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			while ( (line = br.readLine()) != null ) {
				if( !"".equals(line) ){
					if( set.contains(line.trim()) ) continue;//不允许重复影响i值
					set.add( line.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return set;
	}
	
	/**
	 * 从文本文件中读取
	 * 返回的是单行文本
	 */
	public static String readFromTxtBySingle(String path) {
		FileReader fr = null;//文件文本流
		BufferedReader br = null;//缓冲文本流
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			while ( (line = br.readLine() ) != null) {
				if( !"".equals(line) ){
					sb.append( line+" ");
				}
			}
			br.close();//关闭资源
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
