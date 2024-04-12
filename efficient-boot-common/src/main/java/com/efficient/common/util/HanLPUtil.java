package com.efficient.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.entity.HotWord;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 汉语言处理包
 *
 * @author TMW
 * @since 2024/4/12 9:57
 */
public class HanLPUtil {
    private static final String FILTER_TERMS = "`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？";

    public static List<String> skipDictList = new ArrayList<>();
    /**
     * 词性查看：<a href="https://www.hankcs.com/nlp/part-of-speech-tagging.html#h2-8">...</a>
     */
    public static List<String> skipNatureList = new ArrayList<>();
    /**
     * 词向量  <a href="https://github.com/hankcs/HanLP/wiki/word2vec">...</a>
     */
    public static DocVectorModel docVectorModel;

    /**
     * 热词分析
     *
     * @param content 内容
     * @return 热词集合
     */
    public static List<HotWord> hotAnalyse(String content) {
        if (StrUtil.isBlank(content)) {
            return null;
        }
        List<Term> termList = CoreStopWordDictionary.apply(StandardTokenizer.segment(content));
        // 用HashMap存词频数据
        HashMap<String, Integer> pplHashMap = new HashMap<>();
        // 用HashMap存词性数据
        HashMap<String, String> pplFlagMap = new HashMap<>();
        List<HotWord> result = new ArrayList<>();

        outerLoop:
        for (Term term : termList) {
            // 词性过滤
            for (String skipNature : skipNatureList) {
                if (term.nature.startsWith(skipNature)) {
                    continue outerLoop;
                }
            }
            // 停用词过滤
            for (String skipDict : skipDictList) {
                if (skipDict.equals(term.word)) {
                    continue outerLoop;
                }
            }

            pplFlagMap.put(term.word, term.nature.toString());
            if (!pplHashMap.containsKey(term.word)) {
                pplHashMap.put(term.word, 1);
            } else {
                pplHashMap.compute(term.word, (k, tmpCount) -> tmpCount + 1);
            }
        }

        pplHashMap.forEach((k, v) -> {
            HotWord hotWord = new HotWord();
            hotWord.setWord(k);
            hotWord.setNature(pplFlagMap.get(k));
            hotWord.setCount(v);
            result.add(hotWord);
        });
        return result;
    }

    /**
     * 计算相似度
     *
     * @param sentence1 文本1
     * @param sentence2 文本2
     * @return
     */
    public static double vecSimilarity(String sentence1, String sentence2) {
        return docVectorModel.similarity(sentence1, sentence2);
    }

    public static void init(List<String> includeDictList, List<String> skipDictList, List<String> skipNatureList, String word2vecPath) throws IOException {
        if (CollUtil.isNotEmpty(includeDictList)) {
            includeDictList.forEach(CustomDictionary::add);
        }
        if (CollUtil.isNotEmpty(skipDictList)) {
            HanLPUtil.skipDictList = skipDictList;
        }
        if (CollUtil.isNotEmpty(skipNatureList)) {
            HanLPUtil.skipNatureList = skipNatureList;
        }
        if (CollUtil.isEmpty(skipNatureList)) {
            // 数词过滤
            HanLPUtil.skipNatureList.add("m");
            // 量词过滤
            HanLPUtil.skipNatureList.add("q");
            // 时间词过滤
            HanLPUtil.skipNatureList.add("t");
            // 标点符号过滤
            HanLPUtil.skipNatureList.add("w");
        }
        if (StrUtil.isNotBlank(word2vecPath)) {
            docVectorModel = new DocVectorModel(new WordVectorModel(word2vecPath));
        }
    }

    /**
     * 相似性比较
     *
     * @param sentence1 句子1
     * @param sentence2 句子2
     * @return
     */
    public static double getSimilarity(String sentence1, String sentence2) {
        List<String> sent1Words = getSplitWords(sentence1);
        List<String> sent2Words = getSplitWords(sentence2);
        List<String> allWords = mergeList(sent1Words, sent2Words);

        // int[] statistic1 = statistic(allWords, sent1Words);
        // int[] statistic2 = statistic(allWords, sent2Words);
        //
        // double dividend = 0;
        // double divisor1 = 0;
        // double divisor2 = 0;
        // for (int i = 0; i < statistic1.length; i++) {
        //     dividend += statistic1[i] * statistic2[i];
        //     divisor1 += Math.pow(statistic1[i], 2);
        //     divisor2 += Math.pow(statistic2[i], 2);
        // }
        //
        // return dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
        RealVector vector1 = vectorize(sent1Words, allWords);
        RealVector vector2 = vectorize(sent2Words, allWords);

        return cosineSimilarity(vector1, vector2);
    }

    /**
     * 分词（过滤特殊字符的词）
     */
    private static List<String> getSplitWords(String sentence) {
        return HanLP.segment(sentence).stream().filter(s -> !StrUtil.startWithAny(String.valueOf(s.nature.firstChar()), skipNatureList.toArray(new String[0]))).map(s -> s.word).collect(Collectors.toList());
    }

    /**
     * 合并两个分词结果（去重）
     */
    private static List<String> mergeList(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        return result.stream().distinct().collect(Collectors.toList());
    }

    private static RealVector vectorize(List<String> words, List<String> allWords) {
        double[] vector = new double[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.get(i);
            vector[i] = words.contains(word) ? 1.0 : 0.0; // 使用二进制表示词语是否出现
        }
        return new ArrayRealVector(vector);
    }

    private static double cosineSimilarity(RealVector vector1, RealVector vector2) {
        return vector1.cosine(vector2);
    }

    /**
     * 词频分析
     *
     * @param allWords  去重后所有词列表
     * @param sentWords 分析词列表
     * @return
     */
    private static int[] statistic(List<String> allWords, List<String> sentWords) {
        int[] result = new int[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            result[i] = Collections.frequency(sentWords, allWords.get(i));
        }
        return result;
    }
}
