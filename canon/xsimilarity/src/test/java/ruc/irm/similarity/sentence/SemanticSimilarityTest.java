package ruc.irm.similarity.sentence;

import org.junit.Test;

import ruc.irm.similarity.sentence.morphology.SemanticSimilarity;

/**
 * 基于语义的词形和词序句子相似度计算
 * 
 * @author aool
 *
 */
public class SemanticSimilarityTest {

	@Test
	public void test() {
		String s1 = "一个伟大的国家，中国";
		String s2 = "中国是一个伟大的国家";

		// s1="修改下密码";
		// s2="密码修改";
		SemanticSimilarity sim = SemanticSimilarity.getInstance();
		System.out.println("sim ==> " + sim.getSimilarity(s1, s2));

		System.out.println("非洲人=亚洲人" + sim.getSimilarity("非洲人", "亚洲人"));

		System.out.println("人民=公民" + sim.getSimilarity("人民", "公民"));

		System.out.println("人民=群众" + sim.getSimilarity("人民", "群众"));

		System.out.println("人民=国民" + sim.getSimilarity("人民", "国民"));

		System.out.println("人民=同志" + sim.getSimilarity("人民", "同志"));

		System.out.println("人民=良民" + sim.getSimilarity("人民", "良民"));

		System.out.println("人民=志愿者" + sim.getSimilarity("人民", "志愿者"));

		System.out.println("骄傲=仔细" + sim.getSimilarity("骄傲", "仔细"));

		System.out.println("词语=短语" + sim.getSimilarity("词语", "短语"));

		System.out.println("词语=词汇" + sim.getSimilarity("词语", "词汇"));

		System.out.println("保温效果=保温性能" + sim.getSimilarity("保温效果", "保温性能"));

		System.out.println("保温=保温效果" + sim.getSimilarity("保温", "保温效果"));

		System.out.println("上海=魔都" + sim.getSimilarity("上海", "魔都"));

		System.out.println("保温杯=保温效果" + sim.getSimilarity("保温杯", "保温效果"));

		System.out.println("保温杯=保温" + sim.getSimilarity("保温杯", "保温"));

		System.out.println("保温效果=保温时间" + sim.getSimilarity("保温效果", "保温时间"));

		System.out.println("保温度=保温效果" + sim.getSimilarity("保温效果", "保温度"));

		System.out.println("保温=保温效果" + sim.getSimilarity("保温效果", "保温"));

		System.out.println("保温=保温度" + sim.getSimilarity("保温", "保温度"));

		System.out.println("保温度=保温性能" + sim.getSimilarity("保温性能", "保温度"));

		System.out.println("保温杯=保温套" + sim.getSimilarity("保温杯", "保温套"));

		System.out.println("开心=保温" + sim.getSimilarity("开心", "保温"));

		System.out.println("哀伤=保温" + sim.getSimilarity("哀伤", "保温"));

		System.out.println("哀伤=时尚" + sim.getSimilarity("哀伤", "时尚"));

		System.out.println("这个杯子很好看=杯子外观很漂亮" + sim.getSimilarity("这个杯子很好看", "杯子外观很漂亮"));

		System.out.println("杯子保温效果不错哈=杯子保温性能很棒奥" + sim.getSimilarity("杯子保温效果不错哈", "杯子保温性能很棒奥"));

		System.out.println("杯子真丑=杯子外观很漂亮" + sim.getSimilarity("杯子真丑", "杯子外观很漂亮"));

		System.out.println("杯子保温效果真不赖=杯子保温非常好" + sim.getSimilarity("杯子保温效果真不赖", "杯子保温非常好"));

		System.out.println("杯子保温效果真差=杯子保温非常好" + sim.getSimilarity("杯子保温效果真差", "杯子保温非常好"));

		System.out.println("杯子很保温=杯子很丑" + sim.getSimilarity("杯子很保温", "杯子很丑"));

		System.out.println("湿=温" + sim.getSimilarity("湿", "温"));
		
		 System.out.println("差=好"+sim.getSimilarity("差", "好"));
		
		 System.out.println("很好=好"+sim.getSimilarity("很好", "好"));
		 
		 System.out.println("非常好=很好"+sim.getSimilarity("非常好", "很好"));
		 
		 System.out.println("非常好=好"+sim.getSimilarity("非常好", "好"));
		 
		 System.out.println("不好=好"+sim.getSimilarity("不好", "好"));
		 
		 System.out.println("非常差=非常好"+sim.getSimilarity("非常差", "非常好"));
		 
		 System.out.println("非常差=很好"+sim.getSimilarity("非常差", "很好"));
		 
		 System.out.println("非常差=不好"+sim.getSimilarity("非常差", "不好"));
		 
		 System.out.println("非常差=好"+sim.getSimilarity("非常差", "好"));

		 System.out.println("给力=很好"+sim.getSimilarity("给力", "很好"));
		 
		 System.out.println("给力=非常好"+sim.getSimilarity("给力", "非常好"));
		 
		 System.out.println("给力=不好"+sim.getSimilarity("给力", "不好"));
		 
		 System.out.println("给力=很差"+sim.getSimilarity("给力", "很差"));
		 
		 System.out.println("给力=差"+sim.getSimilarity("给力", "差"));
		 
		 System.out.println("一般=好"+sim.getSimilarity("一般", "好"));
	}

}
