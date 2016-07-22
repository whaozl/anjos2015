package com.jxutcm.nlpir;

public class ThreadNLP implements Runnable {
	private NLPIR nlpir;
	
	public ThreadNLP(NLPIR nlpir){
		this.nlpir = nlpir;
	}
	
	@Override
	public void run() {
		 System.out.println(nlpir.paragraphProcess("中国人民解放军保卫祖国", 1));
	}
}
