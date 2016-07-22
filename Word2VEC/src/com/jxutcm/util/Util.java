package com.jxutcm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {
	
	/**
	 * 按照Key排序
	 * flag = 1 正序
	 * flag =-1 倒序
	 */
	public static <K, V> Map<K, V> sortByKey(Map<K, V> map, final int flag){
		Map<K, V> mm = new HashMap<K, V>();
		List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K,V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return flag * o1.getKey().toString().compareTo(o2.getKey().toString());//转换为String比较
			}
		});
		for(Map.Entry<K, V> entry : list){
			mm.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
	
	/**
	 * 按照Key排序
	 * flag = 1 正序
	 * flag =-1 倒序
	 */
	public static <K, V> Map<K, V> sortByValue(Map<K, V> map, final int flag){
		Map<K, V> mm = new HashMap<K, V>();
		List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K,V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return flag * o1.getValue().toString().compareTo(o2.getValue().toString());//转换为String比较
			}
		});
		for(Map.Entry<K, V> entry : list){
			mm.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
}
