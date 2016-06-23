package com.whaozl.anjos.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象序列化
 * @author whaozl
 */
public class SerializableUtils {
	
	/**
	 * 写
	 */
	public static void save(Object object, String path) {
		File file = new File(path);
		FileOutputStream fos = null;
		BufferedOutputStream bos=null;
		ObjectOutputStream oos = null;
		try {
			if(file.exists()){
				file.delete();
			}else{
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos=new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);//括号内参数为要保存java对象
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读
	 */
	public static Object open(String path){
		FileInputStream fis = null;
		BufferedInputStream bis=null;
		ObjectInputStream ois = null;
		File file = new File(path);
		Object object=null;
		try {
			if(file.exists()){
				fis = new FileInputStream(file);
				bis=new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				object = ois.readObject();//强制类型转换
				ois.close();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
