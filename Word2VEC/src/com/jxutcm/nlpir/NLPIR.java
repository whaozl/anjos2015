package com.jxutcm.nlpir;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import com.jxutcm.util.PropertiesUtils;
import com.sun.jna.Native;


public class NLPIR {
	
	private Properties properties=PropertiesUtils.getProperties( this.getClass().getResource("/com/jxutcm/config/jxutcm.properties") );
	
	// 定义并初始化接口的静态变量 这一个语句是来加载 dll 的， 注意 dll 文件的路径
	//可以是绝对路径也可以是相对路径，只需要填写 dll 的文件名，不能加后缀
	//Windows下的加载方式。如果需要支持Linux，需要修改这一行为libNLPIR.so的路径
	private CLibrary Instance = (CLibrary) Native.loadLibrary(properties.getProperty("NLPIR_ROOT_WIN") ,
			CLibrary.class);
	//是否已初始化
	private boolean initFlag = false;
	
	/**
	 * 初始化函数
	 */
	public boolean init(){
		String argu = "d:/anjos/ICTCLAS";
		//String system_charset = "UTF-8";
		int charset_type = 1;
		int init_flag = Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;
		if( 0 == init_flag ){
			nativeBytes = Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			return false;
		}
		initFlag = true;
		return true;
	}
	
	/**
	 * 分词退出
	 */
	public boolean exit(){
		try {
			Instance.NLPIR_Exit();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		initFlag = false;
		return true;
	}
	
	/**
	 * 将语句str进行分词
	 * flag= 0 表示不带词性；flag = 1带词性
	 */
	public String paragraphProcess(String str, int flag){
		String nativeBytes = null;
		try{
			nativeBytes = Instance.NLPIR_ParagraphProcess(str, flag);
		} catch(Exception e){
			e.printStackTrace();
		}
		return nativeBytes;
	}
	
	/**
	 * genuine文件路径进行分词
	 * 输出结果直接输出到文件
	 * flag= 0 表示不带词性；flag = 1带词性
	 */
	public void fileProcess(String utf8File, String utf8FileResult, int flag){
		try{
			Instance.NLPIR_FileProcess( utf8File, utf8FileResult, flag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据字符串内容提取关键词
	 */
	public String getKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut){
		String nativeBytes = null;
		try{
			nativeBytes = Instance.NLPIR_GetKeyWords(sLine, nMaxKeyLimit, bWeightOut);
		} catch(Exception e){
			e.printStackTrace();
		}
		return nativeBytes;
	}
	
	/**
	 * 根据文件路径提取关键词
	 */
	public String getFileKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut){
		String nativeBytes = null;
		try{
			nativeBytes = Instance.NLPIR_GetFileKeyWords(sLine, nMaxKeyLimit, bWeightOut);
		} catch(Exception e){
			e.printStackTrace();
		}
		return nativeBytes;
	}
	
	/**
	 * 添加用户词典
	 */
	public int addUserWord(String word){
		return Instance.NLPIR_AddUserWord(word);
	}
	
	/**
	 * 删除用户词典
	 */
	public int delUsrWord(String word){
		return Instance.NLPIR_DelUsrWord(word);
	}
	
	public CLibrary getInstance(){
		return Instance;
	}
	
	/**
	 * 判断是否已进行了初始化  false=否
	 */
	public boolean isInitFlag() {
		return  initFlag;
	}
	
	/**
	 * 将字符串aidString按照编码格式ori_encoding进行转码成new_encoding
	 */
	public String transString(String aidString, String ori_encoding, String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
