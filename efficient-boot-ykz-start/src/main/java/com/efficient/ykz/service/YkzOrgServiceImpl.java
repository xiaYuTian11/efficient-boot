package com.efficient.ykz.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.ykz.api.YkzOrgService;
import com.efficient.ykz.dao.YkzOrgMapper;
import com.efficient.ykz.model.converter.YkzOrgConverter;
import com.efficient.ykz.model.entity.YkzOrgDb;
import com.efficient.ykz.model.vo.YkzOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * efficient_ykz_org 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Service
@ConditionalOnProperty(name = "com.efficient.ykz.userCenter.db", havingValue = "true")
public class YkzOrgServiceImpl extends ServiceImpl<YkzOrgMapper, YkzOrgDb> implements YkzOrgService {

    @Autowired
    private YkzOrgConverter ykzOrgConverter;

    @Override
    public void saveErrorMsg(String errorMsg) {
        YkzOrgDb db = new YkzOrgDb();
        db.setPullTime(new Date());
        db.setErrorInfo(errorMsg);
        this.save(db);
    }

    @Override
    public void saveOne(YkzOrg ykzOrg) {
        YkzOrgDb db = ykzOrgConverter.ykz2Db(ykzOrg);
        db.setYkzId(ykzOrg.getId());
        db.setId(null);
        db.setPullTime(new Date());
        this.save(db);
    }

    @Override
    public void saveBatchDb(List<YkzOrg> resultList) {
        if (CollUtil.isEmpty(resultList)) {
            return;
        }
        List<YkzOrgDb> ykzOrgDbList = new ArrayList<>(resultList.size());
        Date date = new Date();
        resultList.forEach(ykzOrg -> {
            YkzOrgDb db = ykzOrgConverter.ykz2Db(ykzOrg);
            db.setYkzId(ykzOrg.getId());
            db.setId(null);
            db.setPullTime(date);
            ykzOrgDbList.add(db);
        });
        this.saveBatch(ykzOrgDbList, DbConstant.BATCH_SIZE);
    }
}
