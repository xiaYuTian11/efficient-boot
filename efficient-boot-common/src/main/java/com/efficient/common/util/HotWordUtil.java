package com.efficient.common.util;

import cn.hutool.core.util.StrUtil;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * https://cloud.tencent.com/developer/article/1919167
 * <p>
 * 词条分析，热词分析
 *
 * @author TMW
 * @since 2023/3/22 10:25
 */
public class HotWordUtil {

    /**
     * 全文本词频统计
     *
     * @param content  文本内容
     * @param useSmart 是否使用 smart
     * @return 词，词频
     * @throws IOException
     */
    public static Map<String, Integer> countTermFrequency(String content, Boolean useSmart) throws IOException {
        // 输出结果 Map
        Map<String, Integer> frequencies = new HashMap<>();
        if (StrUtil.isBlank(content)) {
            return frequencies;
        }
        DefaultConfig conf = new DefaultConfig();
        conf.setUseSmart(useSmart);
        // 使用 IKSegmenter 初始化文本信息并加载词典
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(content), conf);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
            // 过滤单字，也可以过滤其他内容，如数字和单纯符号等内容
            if (lexeme.getLexemeText().length() > 1) {
                final String term = lexeme.getLexemeText();
                // Map 累加操作
                frequencies.compute(term, (k, v) -> {
                    if (v == null) {
                        v = 1;
                    } else {
                        v += 1;
                    }
                    return v;
                });
            }
        }
        return frequencies;
    }

    /**
     * 文本列表词频和词文档频率统计
     *
     * @param docs     文档列表
     * @param useSmart 是否使用只能分词
     * @return 词频列表 词-[词频,文档频率]
     * @throws IOException
     */
    public static Map<String, Integer[]> countTFDF(List<String> docs, boolean useSmart) throws IOException {
        // 输出结果 Map
        Map<String, Integer[]> frequencies = new HashMap<>();
        for (String doc : docs) {
            if (StrUtil.isBlank(doc)) {
                continue;
            }
            DefaultConfig conf = new DefaultConfig();
            conf.setUseSmart(useSmart);
            // 使用 IKSegmenter 初始化文本信息并加载词典
            IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(doc), conf);
            Lexeme lexeme;
            // 用于文档频率统计的 Set
            Set<String> terms = new HashSet<>();
            while ((lexeme = ikSegmenter.next()) != null) {
                if (lexeme.getLexemeText().length() > 1) {
                    final String text = lexeme.getLexemeText();
                    // 进行词频统计
                    frequencies.compute(text, (k, v) -> {
                        if (v == null) {
                            v = new Integer[]{1, 0};
                        } else {
                            v[0] += 1;
                        }
                        return v;
                    });
                    terms.add(text);
                }
            }
            // 进行文档频率统计：无需初始化 Map，统计词频后 Map 里面必有该词记录
            for (String term : terms) {
                frequencies.get(term)[1] += 1;
            }
        }
        return frequencies;
    }

    /**
     * 按出现次数，从高到低排序取 TopN
     *
     * @param data 词和排序数字对应的 Map
     * @param topN 词云展示的 TopN
     * @return 前 N 个词和排序值
     */
    public static Map<String, Integer> order(Map<String, Integer> data, int topN) {
        return data.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .skip(0)
                .limit(topN)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
