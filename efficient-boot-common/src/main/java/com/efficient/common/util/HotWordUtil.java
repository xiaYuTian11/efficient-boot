package com.efficient.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.entity.HotWord;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 词条分析，热词分析
 *
 * @author TMW
 * @since 2023/3/22 10:25
 */
public class HotWordUtil {

    public static List<String> stopList = new ArrayList<>();

    public static void wordInit(List<String> customDictList, List<String> stopDictList) {
        if (cn.hutool.core.collection.CollUtil.isNotEmpty(customDictList)) {
            customDictList.forEach(CustomDictionary::add);
        }
        if (CollUtil.isNotEmpty(stopDictList)) {
            stopList = stopDictList;
        }
    }

    public static List<HotWord> analyse(String content) {
        if (StrUtil.isBlank(content)) {
            return null;
        }
        List<Term> termList = CoreStopWordDictionary.apply(StandardTokenizer.segment(content));
        HashMap<String, Integer> pplHashMap = new HashMap<>();//用HashMap存词频数据
        HashMap<String, String> pplFlagMap = new HashMap<String, String>();//用HashMap存词频数据
        List<HotWord> result = new ArrayList<>();

        for (Term term : termList) {
            if (term.nature.startsWith('m')) continue; // 数词过滤
            if (term.nature.startsWith('q')) continue; // 量词过滤
            if (term.nature.startsWith('t')) continue; // 时间词过滤
            if (term.nature.startsWith("w")) continue; // 标点符号过滤
            if (CoreStopWordDictionary.contains(term.word)) continue; // 停用词过滤
            if (stopList.contains(term.word)) continue; // 停用词过滤

            pplFlagMap.put(term.word, term.nature.toString());
            if (!pplHashMap.containsKey(term.word)) {
                pplHashMap.put(term.word, 1);
            } else {
                int tmp_count = pplHashMap.get(term.word);
                pplHashMap.put(term.word, tmp_count + 1);
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
}
