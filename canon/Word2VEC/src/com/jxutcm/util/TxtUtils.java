package com.jxutcm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TxtUtils {
	/**
	 * 创建文件
	 */
	public static boolean createFile(File file) throws Exception {
		boolean flag = false;
		try {
			if (!file.exists()) {
				file.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 读txt文件内容
	 */
	public static String readTxt(File file) throws Exception {
		String result = "";
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader( file );
			br = new BufferedReader( fr );
			String read = null;
			while ( (read = br.readLine()) != null ) {
				result = result + read + "\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		}
		return result;
	}
	
	/**
	 * 写内容到文件中
	 */
	public static boolean writeTxt(String content, File file) throws Exception {
		//RandomAccessFile ra = null;
		boolean flag = false;
		BufferedWriter bw = null;
		try {
			// ra=new RandomAccessFile(fileName,"rw");
			// ra.writeBytes(content);
			bw = new BufferedWriter( new FileWriter(file) );
			bw.write(content);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
		return flag;
	}

	public static void contentToTxt(String filePath, String content) {
		String str = null; // 原有txt内容
		StringBuilder sb = new StringBuilder("");//内容更新
		try {
			File file = new File(filePath);
			if ( file.exists() ) {
				//System.out.print("文件存在");
			} else {
				//System.out.print("文件不存在");
				file.createNewFile();// 不存在则创建
			}
			
			BufferedReader br = new BufferedReader( new FileReader(file) );
			while ( (str = br.readLine()) != null ) {
				sb.append(str).append("\n");
			}
			br.close();
			
			sb.append( content );
			BufferedWriter bw = new BufferedWriter( new FileWriter(file) );
			bw.write( sb.toString() );
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
