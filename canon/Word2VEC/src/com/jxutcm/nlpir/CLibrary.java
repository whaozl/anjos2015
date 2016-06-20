package com.jxutcm.nlpir;

import com.sun.jna.Library;

/**
 * 定义接口CLibrary，继承自com.sun.jna.Library
 * @author whaozl
 */
public interface CLibrary extends Library {
	//初始化函数声明
	public int NLPIR_Init(String sDataPath, int encoding, String sLicenceCode);
	//执行分词函数声明
	public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);
	//提取关键词函数声明
	public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);
	public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);
	//添加用户词典声明
	public int NLPIR_AddUserWord(String sWord);
	//删除用户词典声明
	public int NLPIR_DelUsrWord(String sWord);
	public String NLPIR_GetLastErrorMsg();
	//退出函数声明
	public void NLPIR_Exit();
	//文件分词声明
	public void NLPIR_FileProcess(String utf8File, String utf8FileResult, int i);
}
