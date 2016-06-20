package edu.shu.similarity.cilin;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

/**
 * <p>
 * Created with IntelliJ IDEA. 2015/8/2 21:54
 * </p>
 * <p>
 * ClassName:WordSimilarity
 * </p>
 * <p>
 * Description:<br/>
 * "=" 代表 相等 同义 <br/>
 * "#" 代表 不等 同类 属于相关词语 <br/>
 * "@" 代表 自我封闭 独立 它在词典中既没有同义词, 也没有相关词 <br/>
 * </P>
 *
 * @author Wang Xu
 * @version V1.0.0
 * @since V1.0.0
 */
public class WordSimilarity {
	private final  Logger log = LoggerFactory.getLogger(WordSimilarity.class);

    //定义一些常数先
    private final double a = 0.65;
    private final double b = 0.8;
    private final double c = 0.9;
    private final double d = 0.96;
    private final double e = 0.5;
    private final double f = 0.1;

    private final double degrees = 180;


    //存放的是以词为key，以该词的编码为values的List集合，其中一个词可能会有多个编码
    private static Map<String, ArrayList<String>> wordsEncode = new HashMap<String, ArrayList<String>>();
    //存放的是以编码为key，以该编码多对应的词为values的List集合，其中一个编码可能会有多个词
    private static Map<String, ArrayList<String>> encodeWords = new HashMap<String, ArrayList<String>>();

    /**
     * 读取同义词词林并将其注入wordsEncode和encodeWords
     */
    public void readCiLin() {
        InputStream input = WordSimilarity.class.getClassLoader().getResourceAsStream("cilin.txt");
        List<String> contents = null;
        try {
            contents = IOUtils.readLines(input);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        for (String content : contents) {
            content = Preconditions.checkNotNull(content);
            String[] strsArr = content.split(" ");
            String[] strs = Preconditions.checkNotNull(strsArr);
            String encode = null;
            int length = strs.length;
            if (length > 1) {
                encode = strs[0];//获取编码
            }
            ArrayList<String> encodeWords_values = new ArrayList<String>();
            for (int i = 1; i < length; i++) {
                encodeWords_values.add(strs[i]);
            }
            encodeWords.put(encode, encodeWords_values);//以编码为key，其后所有值为value
            for (int i = 1; i < length; i++) {
                String key = strs[i];
                if (wordsEncode.containsKey(strs[i])) {
                    ArrayList<String> values = wordsEncode.get(key);
                    values.add(encode);
                    //重新放置回去
                    wordsEncode.put(key, values);//以某个value为key，其可能的所有编码为value
                } else {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(encode);
                    wordsEncode.put(key, temp);
                }
            }
        }
    }

    /**
     * 对外暴露的接口，返回两个词的相似度的计算结果
     *
     * @param word1
     * @param word2
     * @return 相似度值
     */
    public double getSimilarity(String word1, String word2) {
        //如果比较词没有出现在同义词词林中，则相似度为0
        if (!wordsEncode.containsKey(word1) || !wordsEncode.containsKey(word2)) {
            return 0;
        }
        //获取第一个词的编码
        ArrayList<String> encode1 = getEncode(word1);
        //获取第二个词的编码
        ArrayList<String> encode2 = getEncode(word2);

        double maxValue = 0;//最终的计算结果值，取所有相似度里面结果最大的那个
        for (String e1 : encode1) {
            for (String e2 : encode2) {
                log.info(e1);
                log.info(e2);
                String commonStr = getCommonStr(e1, e2);
                int length = StringUtils.length(commonStr);
                double k = getK(e1, e2);
                double n = getN(commonStr);
                log.info("k--" + k);
                log.info("n--" + n);
                log.info("length--" + length);
                double res = 0;
                //如果有一个以“@”那么表示自我封闭，肯定不在一棵树上，直接返回f
                if (e1.endsWith("@") || e2.endsWith("@") || 0 == length) {
                    if (f > maxValue) {
                        maxValue = f;
                    }
                    continue;
                }
                if (1 == length) {
                    //说明在第二层上计算
                    res = a * cos(n * PI / degrees) * ((n - k + 1) / n);
                } else if (2 == length) {
                    //说明在第三层上计算
                    res = b * cos(n * PI / degrees) * ((n - k + 1) / n);
                } else if (4 == length) {
                    //说明在第四层上计算
                    res = c * cos(n * PI / degrees) * ((n - k + 1) / n);
                } else if (5 == length) {
                    //说明在第五层上计算
                    res = d * cos(n * PI / degrees) * ((n - k + 1) / n);
                } else {
                    //注意不存在前面七个字符相同，而结尾不同的情况，所以这个分支一定是8个字符都相同，那么只需比较结尾即可
                    if (e1.endsWith("=") && e2.endsWith("=")) {
                        //说明两个完全相同
                        res = 1;
                    } else if (e1.endsWith("#") && e2.endsWith("#")) {
                        //只有结尾不同，说明结尾是“#”
                        res = e;
                    }
                }
                log.info("res :" + res);
                if (res > maxValue) {
                    maxValue = res;
                }
            }
        }
        return maxValue;
    }

    /**
     * 判断一个词在同义词词林中是否是自我封闭的，是否是独立的
     *
     * @param source
     * @return
     */
    private boolean isIndependent(String source) {
        Iterator<String> iter = wordsEncode.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (StringUtils.equalsIgnoreCase(key, source)) {
                ArrayList<String> values = wordsEncode.get(key);
                for (String value : values) {
                    if (value.endsWith("@")) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * 根据word的内容，返回其对应的编码
     *
     * @param word
     * @return
     */
    public ArrayList<String> getEncode(String word) {
        return wordsEncode.get(word);
    }

    /**
     * 计算N的值，N表示所在分支层分支数，如：人 Aa01A01= 和 少儿 Ab04B01=，以A开头的子分支只有14个
     * 这一点在论文中说的非常不清晰，所以以国人的文章进行编码真是痛苦
     *
     * @param encodeHead 输入两个字符串的公共开头
     * @return 经过计算之后得到N的值
     */
    public int getN(String encodeHead) {
        int length = StringUtils.length(encodeHead);
        switch (length) {
            case 1:
                return getCount(encodeHead, 2);
            case 2:
                return getCount(encodeHead, 4);
            case 4:
                return getCount(encodeHead, 5);
            case 5:
                return getCount(encodeHead, 7);
            default:
                return 0;
        }
    }

    public int getCount(String encodeHead, int end) {
        Set<String> res = new HashSet<String>();
        Iterator<String> iter = encodeWords.keySet().iterator();
        while (iter.hasNext()) {
            String curr = iter.next();
            if (curr.startsWith(encodeHead)) {
                String temp = curr.substring(0, end);
                if (res.contains(temp)) {
                    continue;
                } else {
                    res.add(temp);
                }
            }
        }
        return res.size();
    }

    /**
     * @param encode1 第一个编码
     * @param encode2 第二个编码
     * @return 这两个编码对应的分支间的距离，用k表示
     */
    public int getK(String encode1, String encode2) {
        String temp1 = encode1.substring(0, 1);
        String temp2 = encode2.substring(0, 1);
        if (StringUtils.equalsIgnoreCase(temp1, temp2)) {
            temp1 = encode1.substring(1, 2);
            temp2 = encode2.substring(1, 2);
        } else {
            return Math.abs(temp1.charAt(0) - temp2.charAt(0));
        }
        if (StringUtils.equalsIgnoreCase(temp1, temp2)) {
            temp1 = encode1.substring(2, 4);
            temp2 = encode2.substring(2, 4);
        } else {
            return Math.abs(temp1.charAt(0) - temp2.charAt(0));
        }
        if (StringUtils.equalsIgnoreCase(temp1, temp2)) {
            temp1 = encode1.substring(4, 5);
            temp2 = encode2.substring(4, 5);
        } else {
            return Math.abs(Integer.valueOf(temp1) - Integer.valueOf(temp2));
        }
        if (StringUtils.equalsIgnoreCase(temp1, temp2)) {
            temp1 = encode1.substring(5, 7);
            temp2 = encode2.substring(5, 7);
        } else {
            return Math.abs(temp1.charAt(0) - temp2.charAt(0));
        }
        return Math.abs(Integer.valueOf(temp1) - Integer.valueOf(temp2));
    }

    /**
     * 获取编码的公共部分字符串
     *
     * @param encode1
     * @param encode2
     * @return
     */
    public String getCommonStr(String encode1, String encode2) {
        int length = StringUtils.length(encode1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (encode1.charAt(i) == encode2.charAt(i)) {
                sb.append(encode1.charAt(i));
            } else {
                break;
            }
        }
        int sbLen = StringUtils.length(sb);
        //注意第三层和第五层均有两个字符，所以长度不可能出现3和6的情况
        if (sbLen == 3 || sbLen == 6) {
            sb.deleteCharAt(sbLen - 1);
        }

        return String.valueOf(sb);
    }

    @Test
    public void testGetN() {
        this.readCiLin();
        int a = getN("A");
        System.out.println(a);
    }

    @Test
    public void testGetK() {
        int k = getK("Aa01A01=", "Aa01A01=");
        System.out.println(k);
    }

    @Test
    public void testGetCommonStr() {
        String commonStr = getCommonStr("Aa01A01=", "Aa01A03=");
        System.out.println(commonStr);
    }

    @Test
    public void testGetSimilarity() {
        this.readCiLin();
        double similarity = getSimilarity("人民", "国民");
        System.out.println("人民--" + "国民:" + similarity);
        similarity = getSimilarity("人民", "群众");
        System.out.println("人民--" + "群众:" + similarity);
        similarity = getSimilarity("人民", "党群");
        System.out.println("人民--" + "党群:" + similarity);
        similarity = getSimilarity("人民", "良民");
        System.out.println("人民--" + "良民:" + similarity);
        similarity = getSimilarity("人民", "同志");
        System.out.println("人民--" + "同志:" + similarity);
        similarity = getSimilarity("人民", "成年人");
        System.out.println("人民--" + "成年人:" + similarity);
        similarity = getSimilarity("人民", "市民");
        System.out.println("人民--" + "市民:" + similarity);
        similarity = getSimilarity("人民", "亲属");
        System.out.println("人民--" + "亲属:" + similarity);
        similarity = getSimilarity("人民", "志愿者");
        System.out.println("人民--" + "志愿者:" + similarity);
        similarity = getSimilarity("人民", "先锋");
        System.out.println("人民--" + "先锋:" + similarity);
        System.out.println("信件--" + "邮件:" + getSimilarity("信件", "邮件"));
        System.out.println("人民--" + "公民:" + getSimilarity("人民", "公民"));
        System.out.println("保温|保暖:"+ getSimilarity("保温", "保暖"));
        System.out.println("效果|性能:"+ getSimilarity("效果", "性能"));
        System.out.println("粉红|红色:"+ getSimilarity("粉红", "红色"));
        System.out.println("北京|首都:"+ getSimilarity("北京", "首都"));
        System.out.println("上海|魔都:"+ getSimilarity("上海", "魔都"));
        System.out.println("首都|帝都:"+ getSimilarity("首都", "帝都"));
        System.out.println("智慧|智能:"+ getSimilarity("智慧", "智能"));

    }

    @Test
    public void testGetSimilarity2() {
    	WordSimilarity sim =new WordSimilarity();
    	sim.readCiLin();
    	
    	 System.out.println( "非洲人=亚洲人"+sim.getSimilarity("非洲人", "亚洲人") );
    	 
         System.out.println("人民=公民"+sim.getSimilarity("人民", "公民"));
         
         System.out.println("人民=群众"+sim.getSimilarity("人民", "群众"));
         
         System.out.println("人民=国民"+sim.getSimilarity("人民", "国民"));
         
         System.out.println("人民=同志"+sim.getSimilarity("人民", "同志"));
         
         System.out.println("人民=良民"+sim.getSimilarity("人民", "良民"));
         
         System.out.println("人民=志愿者"+sim.getSimilarity("人民", "志愿者"));
        
        System.out.println("骄傲=仔细"+sim.getSimilarity("骄傲", "仔细"));
        
        System.out.println("词语=短语"+sim.getSimilarity("词语", "短语"));
        
        System.out.println("词语=词汇"+sim.getSimilarity("词语", "词汇"));
        
        System.out.println("保温效果=保温性能"+sim.getSimilarity("保温效果", "保温性能"));
        
        System.out.println("保温=保温效果"+sim.getSimilarity("保温", "保温效果"));
        
        System.out.println("上海=魔都"+sim.getSimilarity("上海", "魔都"));
        
        System.out.println("保温杯=保温效果"+sim.getSimilarity("保温杯", "保温效果"));
        
        System.out.println("保温杯=保温"+sim.getSimilarity("保温杯", "保温"));
        
        System.out.println("保温效果=保温时间"+sim.getSimilarity("保温效果", "保温时间"));
        
        System.out.println("保温度=保温效果"+sim.getSimilarity("保温效果", "保温度"));
        
        System.out.println("保温=保温效果"+sim.getSimilarity("保温效果", "保温"));
        
        System.out.println("保温=保温度"+sim.getSimilarity("保温", "保温度"));
        
        System.out.println("保温度=保温性能"+sim.getSimilarity("保温性能", "保温度"));
        
        System.out.println("保温杯=保温套"+sim.getSimilarity("保温杯", "保温套"));
        
        System.out.println("开心=保温"+sim.getSimilarity("开心", "保温"));
        
        System.out.println("哀伤=保温"+sim.getSimilarity("哀伤", "保温"));
        
        System.out.println("哀伤=时尚"+sim.getSimilarity("哀伤", "时尚"));
        
        System.out.println("这个杯子很好看=杯子外观很漂亮"+sim.getSimilarity("这个杯子很好看", "杯子外观很漂亮"));
        
        System.out.println("杯子保温效果不错哈=杯子保温性能很棒奥"+sim.getSimilarity("杯子保温效果不错哈", "杯子保温性能很棒奥"));
        
        System.out.println("杯子真丑=杯子外观很漂亮"+sim.getSimilarity("杯子真丑", "杯子外观很漂亮"));
        
        System.out.println("杯子保温效果真不赖=杯子保温非常好"+sim.getSimilarity("杯子保温效果真不赖", "杯子保温非常好"));
        
        System.out.println("杯子保温效果真差=杯子保温非常好"+sim.getSimilarity("杯子保温效果真差", "杯子保温非常好"));
        
        System.out.println("杯子很保温=杯子很丑"+sim.getSimilarity("杯子很保温", "杯子很丑"));
        
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
