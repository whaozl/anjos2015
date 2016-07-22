package com.ansj.demo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.ansj.vec.Learn;
import com.ansj.vec.Word2VEC;
import com.jxutcm.nlpir.NLPIR;
import com.jxutcm.util.StopWordsHandler;
import com.jxutcm.util.WhaozlReader;

/**
 * 重复样本未考虑
 * 多个类别下的所有样本
 * @author aool
 */
public class Demo1 {

    public static void main(String[] args) throws IOException {
    	File corpusResult = new File("corpus\\result.txt");//result.txt为分词结�?
        File corpus = new File("corpus/SogouC.mini.20061127/文化");
        
        //循环读取目录和子目录下的所有文件对象
        List<File> files =  new ArrayList<File>();
        listDirectory( corpus, files );
        
        //分词
        wordCut(corpusResult, files);
        
        //分词结果进行训练
        Learn lean = new Learn() ;//Learn和Word2VEC中的wordMap保存了所有词信息
        lean.learnFile(corpusResult) ;
        lean.saveModel(new File("corpus\\vector.txt")) ;
        
        //加载模型中的所有词向量
        Word2VEC w2v = new Word2VEC() ;
        w2v.loadJavaModel("corpus\\vector.txt") ;
        
        //输出所有单词的词向量
        System.out.println("单词总数="+w2v.getWords());
        for(Map.Entry<String, float[]> entry : w2v.getWordMap().entrySet()){
        	//System.out.println( "["+entry.getKey() +"] ="+Arrays.toString( entry.getValue() ) );
        }
        
        //输出与公司距离最近的前topNSize=40的单词
        System.out.println(w2v.distance("公司"));
    }
    
    /**
     * 分词并结果写进文件
     */
	private static void wordCut(File corpusResult, List<File> files) throws IOException{
		FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        StringBuilder sb = null;
        String str = null;
        String[] array = null;
        try{
        	 fos = new FileOutputStream( corpusResult );
        	 bos = new BufferedOutputStream( fos );
     		NLPIR nlpir = new NLPIR();
    		nlpir.init();
			for( File file : files){
				if( file.canRead() && file.getName().endsWith(".txt") ){
					str = WhaozlReader.readFromTxtBySingle( file.getAbsolutePath() );
					str = nlpir.paragraphProcess(str, 0);
					array = StopWordsHandler.DropStopWord(str.split(" "));
					sb = new StringBuilder();
					for(String s : array){
						sb.append( s ).append(" ");
					}
					bos.write( sb.toString().getBytes("UTF-8"));
					bos.write("\n".getBytes("UTF-8"));
				}
			}
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
			bos.flush();
			bos.close();
			fos.close();
		}
	}
	
	/**
	 * 遍历目录及子目录下的所有文件并保存
	 * corpus 目录全路径
	 * files 保存文件对象列表
	 */
	public static void listDirectory( File corpus, List<File> files  ){
		if( !corpus.exists() ){
			System.out.println("文件名称不存在");
		}else{
			if(corpus.isFile()){
				files.add( corpus);
			}else{
				File[] temps = corpus.listFiles();
				for( int i=0; i<temps.length; i++){
					listDirectory(temps[ i ], files);
				}
			}
		}
	}
	
}
