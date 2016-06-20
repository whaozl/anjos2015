package bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.ChineseSegment;
import util.StopWordsHandler;

/**
 * 
 * 
 * 为朴素贝叶斯算法专用的词汇信息统计类
 * 这里就没有了单个样本【即单个文档】的概念
 * 
 * 【注意】由于这里采用多项式模型，多项式模型没有文档的概念,这里就去掉文档
 * 
 * @author whaozl
 */
public class TermStatistics {
	//统计: Map<类别,    Map<词,        词信息>>
	public Map<String, Map<String, Term> > termTable;
	//统计: Map<类别,    词出现总数>
	public Map<String, Double> classCountTable;
	//词典(去掉停用词后的)
	public Map<String, Integer> dictionary;
	
	public double allTermCount = 0.0;//所有单词总出现次数
	
	public String path;//语料库存放目录,该目录下分为train和test目录
	public File trainPath;//训练集存放目录
	
	public String[] classifications;//训练预料类别目录
	
	public TermStatistics(){
		this.termTable = new HashMap<String, Map<String, Term>>();
		this.dictionary = new HashMap<String, Integer>();
	}
	
	/**
	 * 从语料库中取出训练集
	 * 【这里前提是 每个类别下的单个文本文件中每行是一个样本，同时在处理时又将每行视为一篇文档 txtMap】
	 * @param Path
	 */
	public void read(String Path){
		this.path = Path;
		this.trainPath = new File(path + File.separator+"train");//训练集目录
		if( !this.trainPath.isDirectory() ){
			throw new IllegalArgumentException("训练集目录错误");
		}
		this.classifications = this.trainPath.list();
		
		Map<String, Term> termMap = null;//一篇文档中的所有词信息
		File classFile=null, txtFile = null;
		String[] txts=null;
		FileReader fr = null;//文件文本流
		BufferedReader br = null;//缓冲文本流
		String line = null;
		String[] words = null;
		Term term = null;
		int dictionaryIndex=0;
		try{
			for( int i = 0; i< this.classifications.length; i++){
				//获取类别目录
				classFile = new File( this.trainPath + File.separator + this.classifications[ i ] );
				//获取类别下的所有文本文件相对路径
				txts = classFile.list();
				if(txts.length == 0) throw new IOException( classFile+ "类别下没有训练样本");
				for ( int j = 0; j< txts.length; j++){
					txtFile = new File( this.trainPath + File.separator + this.classifications[ i ] + File.separator + txts [ j ]);
					if( txtFile.isDirectory() ) continue;//若是目录有问题,不继续往下遍历
					fr =  new FileReader( txtFile );
					br =  new BufferedReader(fr);
					termMap = new HashMap<String, Term>( );//放这里
					while ( (line = br.readLine()) != null ) {
						if( !"".equals(line) ){
							words = ChineseSegment.split(line, " ").split(" ");//中文分词
							words = StopWordsHandler.DropStopWord( words );//去掉停用词
							for(int k=0; k < words.length; k++){
								if(termMap.containsKey( words[ k ] )){
									termMap.get( words[ k ] ).addCount();//加1操作
								}else{
									term = new Term();
									term.addCount();//加1操作
									termMap.put(words[ k ], term);
								}
								if( this.dictionary.containsKey( words[ k ] ) ) continue;
								this.dictionary.put( words[ k ], dictionaryIndex++);//放进词典
							}
						}
					}
					this.termTable.put(this.classifications[ i ], termMap);
				}
			}
			br.close();//关闭资源
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.calculateTermCountOfClass();//统计各类别单词总数
	}
	
	
	/**
	 * 该类统计一个类下的单词总数
	 */
	private void calculateTermCountOfClass(){
		this.classCountTable = new HashMap<String, Double>( this.termTable.size() );
		double sum = 0.0;
		this.allTermCount = 0.0;
		for( Map.Entry<String, Map<String, Term>> classEntry : this.termTable.entrySet() ){
			sum = 0.0;
			for( Map.Entry<String, Term> termEntry : classEntry.getValue().entrySet() ){
				sum += termEntry.getValue().count;
			}
			this.classCountTable.put( classEntry.getKey(), sum );
			this.allTermCount += sum;
		}
	}
	
	

}