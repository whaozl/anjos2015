package Action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;

import svmHelper.svm_scale;

import Base.BaseWordCut;
import Helper.FileHelper;
import Helper.TfIdfHelper;
import UserUi.HomeFrame;

public class WordCut extends BaseWordCut {
	/**
	 * 存放分词后所有文章的单词
	 */
	private HashMap<File, HashMap<String, Integer>> articleWordsMap = new HashMap<File, HashMap<String, Integer>>();

	/**
	 * articleWorsMap对应tf-idf格式
	 */
	private HashMap<File, HashMap<String, Double>> tfIdfMap = new HashMap<File, HashMap<String, Double>>(); 
	
	/**
	 * 字典的所有词项和label 
	 */
	private HashMap<String, Integer> wordsDict = new HashMap<String, Integer>();
	
	private HashMap<String, Integer> classLabel = new HashMap<String, Integer>();
	
	/**
	 * 主界面对象
	 */
	private HomeFrame homeFrame = null; 	
	public WordCut(HomeFrame homeFrame) throws IOException{
		this.homeFrame = homeFrame;
		this.loadWordsDict(new File("trainfile/dictionary.txt"));
		this.classLabel = super.loadClassFromFile(new File("trainfile/classLabel.txt"));
	}
	
	/**
	 * 从文件中加载单词 字典
	 * @param file
	 */
	private void loadWordsDict(File file){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while((temp = reader.readLine()) != null){
				String[] str = temp.split(" ");
				wordsDict.put(str[1], Integer.parseInt(str[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HashMap<File, String> readFile(File[] files) throws Exception{
		int curIndex = 0;
		HashMap<File, String> articles = new HashMap<File, String>();
		for (File file : files) {
			String content = FileHelper.readTxtOrDoc(file);
			articles.put(file, content);
			curIndex ++;
			if(homeFrame != null){
				homeFrame.updateProgressBar(curIndex);
			}
		}
		return articles;
	}
	
	/**
	 * 通过文件名获得类标号 如：政治_1.txt 对于的类别为“政治”
	 * @param className
	 * @return
	 */
	private int getClassLabel(String className){
		String[] arr = className.split("_");
		if (classLabel.containsKey(arr[0])) {
			return classLabel.get(arr[0]);
		}else{
			return -1;
		}
	}
	
	/**
	 * 通过文件的父目录获得类标号 如：政治/1.txt 对于的类别为“政治”
	 * @param className
	 * @return
	 */
	private int getClassLabel(File file){
		//文件的目录即类别的名字
//		System.out.println(file.getPath().toString());   //getPath() 获取完整的路径 ：article\政治法律\1.txt
//														 //getPath()返回值是一个String
//		System.out.println(file.getParentFile().toString()); //getParentFile() 获取父目录：article\政治法律
		                                                     //getParentFile()返回值是一个file
		
		String className = file.getParentFile().getName();
		if (classLabel.containsKey(className)) {
			return classLabel.get(className);
		}else{
			return -1;
		}
	}
	
	/**
	 * 对所有文章进行分词处理   并把最终结果都存进 articleWordsMap：file+artWords
	 * @param files
	 * @throws Exception
	 */
	private void cutWord(File[] files) throws Exception{
		HashMap<File, String> articles = this.readFile(files); 
		Iterator<File> artIterator = articles.keySet().iterator();
		while (artIterator.hasNext()) {
			File file = artIterator.next();
			String name = file.getName();
			String content = articles.get(file);
			HashMap<String, Integer> artWords = this.doCutWord(content);
			this.articleWordsMap.put(file, artWords);
//			System.out.println("【file】:"+file.getName());
//			System.out.println("【content】："+content);
//			for(String key:artWords.keySet()){
//				System.out.println("key:"+key+" value:"+artWords.get(key));
//			}
			//System.out.println(name);
		}
	}
	
	/**
	 * 转成svm语料格式
	 * @param outFile 输出的路径
	 */
	private void convertToSvmFormat(File outFile){
		try {
//			TfIdfHelper tfIdfHelper = new TfIdfHelper(articleWordsMap);
//			this.tfIdfMap = tfIdfHelper.calculate();
			PrintWriter writer = new PrintWriter(outFile);
			Iterator<File> artIterator = articleWordsMap.keySet().iterator();
			while (artIterator.hasNext()) {
				File file = artIterator.next();
				//写入svm语料的类别号 : Integer
				writer.print(getClassLabel(file) + " ");
//				System.out.println("[类标号为] "+getClassLabel(file) + " ");
				
//				writer.print(getClassLabel(file.getName()) + " ");
//				System.out.println(file.getParentFile().getName()+" ");
				
//				HashMap<String, Double> artWords = tfIdfMap.get(file);
				HashMap<String, Integer> artWords = articleWordsMap.get(file);
				Iterator<String> wordsIterator = artWords.keySet().iterator();
				while (wordsIterator.hasNext()) {
					String item = (String) wordsIterator.next();
					int index = -1;
					if(wordsDict.containsKey(item)){
						index = wordsDict.get(item);  //index 表示该关键词在wordDict词典中对应的【编号】
						double tfIdf = artWords.get(item);  //直接用词频作为 关键词的tf-idf值
						//写入index和value
						writer.print(index + ":" + tfIdf + " ");
//						System.out.println(item+": "+index + ":" + tfIdf + " ");
					}				
				}
				writer.println();//写入换行符
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分词、生成libsvm语料的公共接口
	 * 将svm语料保存在testfile/svm.test中
	 * @param files
	 * @return 返回生成的svm.test语料文件
	 * @throws Exception
	 */
	public static File run(File[] files,HomeFrame homeFrame) throws Exception{
		WordCut model = new WordCut(homeFrame);
		model.cutWord(files);
		File outFile = new File("testfile/svmscale.test");
		model.convertToSvmFormat(new File("testfile/svm.test"));  //初步转成libsvm格式，并写入到svm.test
		//scale 参数
		String[] sarg = {"-l","0","-r","trainfile/svm.scale","-o","testfile/svmscale.test","testfile/svm.test"};
		svm_scale.main(sarg);   //svm缩放，真复杂
		return outFile;
	}
	
	public static void main(String[] args) throws Exception{

//		File[] files = new File[]{
//		new File("article/政治法律/1.txt"),
//		new File("article/政治法律/2.txt"),
//		new File("article/艺术/1.txt")
//};
		
		File[] files = new File[]{
				new File("article2/政治法律/zzfl_1.txt"),
				new File("article2/政治法律/zzfl_2.txt"),
				new File("article2/艺术/ys_1.txt")
				};
		run(files,null);
//		BaseWordCut model = new BaseWordCut();
//		model.doCutWord("我是一个中国人，我爱打篮球，我喜欢后仰跳投舟曲县城火箭(21-12)三连胜终止。先发五虎只有1人得分上双，钱德勒-帕森斯15分，德怀特-霍华德9分9个篮板，詹姆斯[微博]-哈登8分，林书豪[微博]6分3个篮板。替补出场的奥姆里-卡斯比15分，阿隆-布鲁克斯[微博]17分。");
	}
	
	
	
}
