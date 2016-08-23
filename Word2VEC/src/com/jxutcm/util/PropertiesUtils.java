package com.jxutcm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * 参考网址： http://blog.csdn.net/boboai612/article/details/9982543
 * @author whaozl
 */
public class PropertiesUtils {

	/**
	 * 读取配置文件
	 */
	public static Properties getProperties(File file) {
		Properties properties = null;// 从文件mdxbu.properties中读取网元ID和模块ID信息
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			properties = new Properties();
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	/**
	 * 读取配置文件
	 */
	public static Properties getProperties(URL url) {
		Properties properties = null;// 从文件mdxbu.properties中读取网元ID和模块ID信息
		InputStream istream = null;
		URLConnection uconn=null;
		try {
			uconn = url.openConnection();
			uconn.setUseCaches(false);
			istream = uconn.getInputStream();
			properties = new Properties();
			properties.load(istream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (istream != null) {
					istream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	/**
	 * 得到属性对应值——单个属性
	 */
	public static String getProperty(String key, File file){
		Properties properties = getProperties(file);
		return properties.getProperty(key);
	}
	
	public static String getProperty(String key, URL url){
		Properties properties = getProperties(url);
		return properties.getProperty(key);
	}
	
	/**
	 * 增加属性文件值
	 */
	public static void addProperties(String key, String value, File file) {
		Properties properties = getProperties(file);
		FileOutputStream fos = null;
		try {
			properties.put(key, value);//增加键值对
			fos = new FileOutputStream(file, true);
			properties.store(fos, "modify properties file");//将properties存储在fos中
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存属性到文件中
	 */
	public static void saveProperties(Properties properties, File file) {
		if (properties == null) {
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, false);//将properties存储在fos中
			properties.store(fos, "modify properties file");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改属性文件
	 * @param key
	 * @param value
	 */
	public static void updateProperties(String key, String value, File file) {
		if (key == null || "".equalsIgnoreCase(key)) {// key为空则返回
			return;
		}
		Properties properties = getProperties(file);
		if (properties == null) {
			properties = new Properties();
		}
		properties.put(key, value);// 保存属性到文件中——会自动覆盖key对应的value值
		saveProperties(properties, file);
	}

	public static void main(String[] args) {
		String path= PropertiesUtils.class.getResource("/").getPath()+"com/huaat/config/huat.properties";
		System.out.println(path);
		File file=new File(path);
		updateProperties("wwwww", "3333", file);
		System.out.println(getProperties(file).get("wwwww"));
	}
}
