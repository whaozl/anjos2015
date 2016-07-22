package ruc.irm.similarity.sentence;

import org.junit.Test;
import ruc.irm.similarity.sentence.morphology.MorphoSimilarity;
import ruc.irm.similarity.sentence.morphology.SemanticSimilarity;

/**
 * 基于词形和词序的句子相似度计算算法
 * @author aool
 *
 */
public class MorphoSimilarityTest {

    @Test
    public void test() {
    	
        String s1 = "一个伟大的国家，中国";
        String s2 = "中国是一个伟大的国家";

        s1="修改下密码";
        s2="密码修改";
        MorphoSimilarity similarity = MorphoSimilarity.getInstance();
        double sim = similarity.getSimilarity(s1, s2);
        System.out.println("sim ==> " + sim);
        
        System.out.println("保温效果=保温性能"+similarity.getSimilarity("保温效果", "保温性能"));
        
        System.out.println("保温=保温效果"+similarity.getSimilarity("保温", "保温效果"));
        
        System.out.println("上海=魔都"+similarity.getSimilarity("上海", "魔都"));
        
        System.out.println("保温杯=保温效果"+similarity.getSimilarity("保温杯", "保温效果"));
        
        System.out.println("保温杯=保温"+similarity.getSimilarity("保温杯", "保温"));
        
        System.out.println("保温效果=保温时间"+similarity.getSimilarity("保温效果", "保温时间"));
        
        System.out.println("保温度=保温效果"+similarity.getSimilarity("保温效果", "保温度"));
        
        System.out.println("保温=保温效果"+similarity.getSimilarity("保温效果", "保温"));
        
        System.out.println("保温=保温度"+similarity.getSimilarity("保温", "保温度"));
        
        System.out.println("保温度=保温性能"+similarity.getSimilarity("保温性能", "保温度"));
        
        System.out.println("保温杯=保温套"+similarity.getSimilarity("保温杯", "保温套"));
    }

}
