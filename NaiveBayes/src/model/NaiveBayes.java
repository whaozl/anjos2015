package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bean.Term;
import bean.TermStatistics;
import util.StopWordsHandler;

/**
 * 朴素贝叶斯算法
 * 
 * @author whaozl
 * @time 2016-01-14
 *
 */
public class NaiveBayes {
	public TermStatistics ts;//训练集统计
	
	/**
	 * 根据数据目录读取文件
	 * 目录下必须有训练子目录如data/百姓网/train
	 * @param Path data/百姓网
	 */
	public NaiveBayes(String Path){
		ts = new TermStatistics();
		ts.read(Path);//产生训练集的ts内容,建立词表信息,建立词典信息
	}
	
	/**
	 * 计算p( C_j | X ) ——给定文本属性(词)向量X 【属于】 类别C_j的概率
	 * p( C_j | X ) = p( X | C_j) * p( C_j ) / p( X )  而p( X )是一个常数,比较去掉即可
	 * 类条件概率 p( X | C_j) ——在给定类别C_J下 文本属性(词)向量X 发生的概率
	 *                     p( X | C_j) = p( x_1 | C_j) * p( x_2 | C_j) * …*p( x_i | C_j) *…* p( x_n | C_j)
	 * 先   验概率p( C_j )——给定类别的概率
	 * 【注意】由于概率和词频已经对数化,所以连乘变成连加
	 * @param X 给定的文本词向量——X=(x_1, x_2, …, x_i, …, x_n)
	 * @param C_j 给定类别
	 */
	public double calculateProb( String[] X, String C_j){
		double sum = 0.0;
		for(int i = 0; i< X.length; i++){//类条件概率连乘
			sum += this.calculateClassConditionalProb( X[ i ], C_j );
		}
		sum += calculateClassProb( C_j );
		return Math.exp( sum );//最后转换为指数
	}
	
	/**
	 * 计算类条件概率  p( x_i | C_j)  —— 给定文本属性(词)X_i 【属于】 类别C_j的概率
	 */
	private double calculateClassConditionalProb( String X_i , String C_j ){
		double V= this.ts.dictionary.size();
		Term term = this.ts.termTable.get( C_j ).get( X_i );//未找到该词
		double N_X_iOfC_j = term == null ? 0 : term.count;
		double N_C_j = this.ts.classCountTable.get( C_j ) ;
		return Math.log(  N_X_iOfC_j + 1 ) - Math.log( N_C_j + V );
	}
	
	/**
	 * 计算 p( C_j )的概率 ——类别C_j的概率
	 */
	private double calculateClassProb( String C_j ){
		double N = this.ts.allTermCount;
		double N_C_j = this.ts.classCountTable.get( C_j ) ;
		return Math.log( N_C_j ) - Math.log( N );
	}
	
	/**
	 * 对给定的单个样本进行预测类别
	 */
	public String classify(String text){
		String[] X = ChineseSegment.split( text, " ").split(" ");
		X = StopWordsHandler.DropStopWord( X );
		String C_j = null;
		String result = null;
		double oldProb = 0.0, newProb = 0.0;
		for( int j = 0; j < this.ts.classifications.length; j++){
			C_j = this.ts.classifications[ j ];
			newProb = this.calculateProb(X, C_j);
			if( newProb < oldProb ) continue;
			result = C_j;
			oldProb = newProb;
		}
		return result;
	}

	/**
	 * 在给定测试集下计算预测类别
	 */
	public String[] predict(String path){
		ArrayList<String> al = new ArrayList<String>();
		FileReader fr = null;//文件文本流
		BufferedReader br = null;//缓冲文本流
		String line;
		try {
			File file = new File(path);
			if( file.isDirectory() ) throw new IOException("测试集不是文件路径");
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			while ( (line = br.readLine()) != null ) {
				if( !"".equals(line) ){
					al.add( this.classify(line) );//预测分类
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] result = new String[ al.size() ];
		al.toArray( result );
		return result;
	}
}

















