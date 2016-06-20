package bean;

/**
 * 每个词的信息
 * @author whaozl
 *
 */
public class Term {
	//public String text;//词的内容 Map的Key自带
	public Double count = 0.0;//词频
	public Double td_idf;//td_idf值——后续备用
	
	public Term(){
		this.count=0.0;
		this.td_idf=0.0;
	}
	
	/**
	 * 计数加1
	 */
	public void addCount(){
		this.count++;
	}
}
