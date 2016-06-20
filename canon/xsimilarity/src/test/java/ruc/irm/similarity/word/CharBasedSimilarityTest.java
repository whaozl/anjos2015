package ruc.irm.similarity.word;

import junit.framework.TestCase;

/**
 * 字面相似度计算方法
 * @author aool
 *
 */
public class CharBasedSimilarityTest extends TestCase {
    public void test() {
        CharBasedSimilarity sim = new CharBasedSimilarity();
        String s1 = "手机";
        String s2 = "飞机";
        System.out.println("手机=飞机："+sim.getSimilarity("手机", "飞机"));
        assertTrue(sim.getSimilarity(s1, s2) > 0);
    }
}
