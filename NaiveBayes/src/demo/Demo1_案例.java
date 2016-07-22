package demo;

import model.NaiveBayes;

/**
 * @author whaozl
 *博客：http://www.cnblogs.com/whaozl/
 */
public class Demo1_案例 {
	
	public static void main(String[] args) {
		String trainPath="data/案例数据";
		NaiveBayes nb = new NaiveBayes(trainPath);
		String[] result1 = nb.predict("data/案例数据/test/苹果/苹果.txt");
		int count1 = 0;
		for( String classify : result1){
			if("苹果".equals(classify)) count1++;
		}
		System.out.println("苹果测试集分类正确率为："+((double)count1)/result1.length);
		
		
		
		String[] result2 = nb.predict("data/案例数据/test/三星/三星.txt");
		int count2 = 0;
		for( String classify : result2){
			if("三星".equals(classify)) count2++;
		}
		System.out.println("三星测试集正确率为："+((double)count2)/result2.length);
		
		
		String[] result3 = nb.predict("data/案例数据/test/手机壳/手机壳.txt");
		int count3 = 0;
		for( String classify : result3){
			if("手机壳".equals(classify)) count3++;
		}
		System.out.println("手机壳测试集正确率为："+((double)count3)/result3.length);
	}
}
