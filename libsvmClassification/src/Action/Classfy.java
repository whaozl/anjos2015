package Action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import svmHelper.svm_predict;
import svmHelper.svm_scale;

public class Classfy {

	/**
	 * 调用分类的公共接口
	 * @return 分类结果列表
	 * @throws IOException
	 */
	public static ArrayList<Double> run() throws IOException{
		//分类：三个文件+两行代码
		String[] parg = {"testfile/svmscale.test","trainfile/svm.model","testfile/result.txt"};
		svm_predict.main(parg);
		
		//下面的代码是读取result.txt中的结果，存到result中
		ArrayList<Double> result = new ArrayList<Double>();
		File file = new File("testfile/result.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String temp = null;
		while((temp = reader.readLine()) != null){
			System.out.println(Double.parseDouble(temp));
			result.add(Double.parseDouble(temp));
		}
		return result;
	}
	
	/**
	 * 保存类别的信息
	 */
	private static HashMap<Double, String> classMap = new HashMap<>();
	private static void loadClassFromFile() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File("trainfile/classLabel.txt")));
		String temp = null;
		while((temp = reader.readLine()) != null){
			String[] str = temp.split(" ");
			classMap.put(Double.parseDouble(str[0]), str[1]);
			System.out.println(Double.parseDouble(str[0]) + " " + str[1]);
		}
	}
	
	private static String getClassByLabel(double label){
		if (classMap.containsKey(label)) {
			return classMap.get(label);
		}else{
			System.out.println(label);
			return "其它";
		}
	}
	
	/**
	 * 1.分类   2.返回带有[分类结果+对应的文件list]的Map   
	 * @param sourceFiles  原始文件集合
	 * @return HashMap<String, ArrayList<File>> result 存储[分类结果+对应的文件list]
	 * @throws IOException
	 */
	public static HashMap<String, ArrayList<File>> run(File[] sourceFiles) throws IOException{
		//真正开始分类
		String[] parg = {"testfile/svmscale.test","trainfile/svm.model","testfile/result.txt"};
		svm_predict.main(parg);
		
		/*
		 * 对分类结果进行转换:类别编号  <-> 类标签
		 */
		loadClassFromFile();
		//从result.txt中取出分类结果存到tempResult
		File file = new File("testfile/result.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		ArrayList<Double> tempResult = new ArrayList<>();
		String temp = null;
		while((temp = reader.readLine()) != null){
			double label = Double.parseDouble(temp);
			tempResult.add(label);
			System.out.println("label:"+label);
		}
		
//		System.out.println(sourceFiles.length);
//		System.out.println(tempResult.size());
		if (sourceFiles.length != tempResult.size()) {
			throw new IOException("Classify-->runfile,传入的文件数组长度不匹配");
		}
		
		HashMap<String, ArrayList<File>> result = new HashMap<>();
		for(int i=0; i<tempResult.size(); i++){
			double label = tempResult.get(i);
			String className = getClassByLabel(label);
			//在result<String, ArrayList<File>>中，相同类标签的文件是放在一个tmpList里
			if (!result.containsKey(className)) {   
				ArrayList<File> tmpList = new ArrayList<>();
				tmpList.add(sourceFiles[i]);
//				System.out.println("sourceFiles[i]:"+sourceFiles[i]);
				result.put(className, tmpList);
			}else{
				ArrayList<File> tmpList = result.get(className);
				tmpList.add(sourceFiles[i]);
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) throws IOException{
		run();
//		loadClassFromFile();
//		run(new File[]{new File("article"),new File("article"),new File("article")});
//
		File[] files = new File[]{
				new File("article2/政治法律/zzfl_1.txt"),
				new File("article2/政治法律/zzfl_2.txt"),
				new File("article2/艺术/ys_1.txt")
				};
		HashMap<String, ArrayList<File>> result=run(files);
		for(String key:result.keySet()){
			System.out.print(key+" : ");
			ArrayList<File> tmpList=result.get(key);
			for(File file:tmpList){
				System.out.println(file.getName());
			}
		}
	}
}
