package com.efficient.cache.util;

import com.efficient.cache.api.CacheUtil;
import com.efficient.cache.constant.CacheConstant;
import com.efficient.cache.constant.ProgressStatus;
import com.efficient.cache.vo.DataProgressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

/**
 * 进度条工具类
 *
 * @author TMW
 * @since 2022/6/13 15:22
 */
@Component
public class ProgressUtil {

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 获取当前
     *
     * @param methodName 方法名称
     * @param token      token
     * @return 进度条标识
     */
    public static String getCurrKey(String methodName, String token) {
        return methodName + "-" + token + System.currentTimeMillis();
    }

    /**
     * @param currDataName 当前进度描述
     * @param ratio        当前进度
     * @param key          唯一标识
     */
    public void running(String currDataName, double ratio, String key) {
        String result = getNumFormat().format(ratio);
        DataProgressVO progressVo = new DataProgressVO();
        progressVo.setCurrDataName(currDataName);
        progressVo.setRatio(result);

        progressVo.setCode(ProgressStatus.RUNNING.getCode());
        cacheUtil.put(CacheConstant.PROGRESS_CACHE, key, progressVo, CacheConstant.CACHE_SHORT_TIME);
    }

    public static NumberFormat getNumFormat() {
        NumberFormat num = NumberFormat.getInstance();
        num.setMaximumFractionDigits(2);
        return num;
    }

    public void success(String currDataName, String key) {
        this.success(currDataName, key, null);
    }

    public void success(String currDataName, String key, Object object) {
        String result = getNumFormat().format(100);
        DataProgressVO progressVo = new DataProgressVO();
        progressVo.setCurrDataName(currDataName);
        progressVo.setRatio(result);
        if (object != null) {
            progressVo.setData(object);
        }
        progressVo.setCode(ProgressStatus.SUCCESS.getCode());
        cacheUtil.put(CacheConstant.PROGRESS_CACHE, key, progressVo, CacheConstant.CACHE_SHORT_TIME);
    }

    public DataProgressVO getByKey(String key) {
        return cacheUtil.get(CacheConstant.PROGRESS_CACHE, key);
    }

    public void fail(String currDataName, String key) {
        this.fail(currDataName, key, null);
    }

    public void fail(String currDataName, String key, Object object) {
        String result = getNumFormat().format(100);
        DataProgressVO progressVo = new DataProgressVO();
        progressVo.setCurrDataName(currDataName);
        progressVo.setRatio(result);
        if (object != null) {
            progressVo.setData(object);
        }
        progressVo.setCode(ProgressStatus.FAIL.getCode());
        cacheUtil.put(CacheConstant.PROGRESS_CACHE, key, progressVo, CacheConstant.CACHE_SHORT_TIME);
    }

}
