package com.efficient.ykz.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.ykz.api.YkzUserPostService;
import com.efficient.ykz.dao.YkzUserPostMapper;
import com.efficient.ykz.model.converter.YkzUserPostConverter;
import com.efficient.ykz.model.entity.YkzUserPostDb;
import com.efficient.ykz.model.vo.YkzUserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 愉快政用户职位信息 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Service
@ConditionalOnProperty(name = "com.efficient.ykz.userCenter.db", havingValue = "true")
public class YkzUserPostServiceImpl extends ServiceImpl<YkzUserPostMapper, YkzUserPostDb> implements YkzUserPostService {

    @Autowired
    private YkzUserPostConverter ykzUserPostConverter;

    @Override
    public void saveErrorMsg(String errorMsg) {
        YkzUserPostDb db = new YkzUserPostDb();
        db.setPullTime(new Date());
        db.setErrorInfo(errorMsg);
        this.save(db);
    }

    @Override
    public void saveBatchDb(List<YkzUserPost> resultList) {
        if (CollUtil.isEmpty(resultList)) {
            return;
        }
        List<YkzUserPostDb> ykzUserDbList = new ArrayList<>(resultList.size());
        Date date = new Date();
        resultList.forEach(ykzOrg -> {
            YkzUserPostDb db = ykzUserPostConverter.ykz2Db(ykzOrg);
            db.setPullTime(date);
            ykzUserDbList.add(db);
        });
        this.saveBatch(ykzUserDbList, DbConstant.BATCH_SIZE);
    }
}
