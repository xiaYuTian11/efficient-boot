package com.efficient.ykz.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.ykz.api.YkzUserService;
import com.efficient.ykz.dao.YkzUserMapper;
import com.efficient.ykz.model.converter.YkzUserConverter;
import com.efficient.ykz.model.entity.YkzUserDb;
import com.efficient.ykz.model.vo.YkzUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 渝快政用户信息 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Service
@ConditionalOnProperty(name = "com.efficient.ykz.userCenter.db", havingValue = "true")
public class YkzUserServiceImpl extends ServiceImpl<YkzUserMapper, YkzUserDb> implements YkzUserService {

    @Autowired
    private YkzUserConverter ykzUserConverter;

    @Override
    public void saveErrorMsg(String errorMsg) {
        YkzUserDb db = new YkzUserDb();
        db.setPullTime(new Date());
        db.setErrorInfo(errorMsg);
        this.save(db);
    }

    @Override
    public void saveOne(YkzUser ykzUser) {
        YkzUserDb db = ykzUserConverter.ykz2Db(ykzUser);
        db.setYkzId(ykzUser.getId());
        db.setId(null);
        db.setPullTime(new Date());
        this.save(db);
    }

    @Override
    public void saveBatchDb(List<YkzUser> resultList) {
        if (CollUtil.isEmpty(resultList)) {
            return;
        }
        List<YkzUserDb> ykzUserDbList = new ArrayList<>(resultList.size());
        Date date = new Date();
        resultList.forEach(ykzOrg -> {
            YkzUserDb db = ykzUserConverter.ykz2Db(ykzOrg);
            db.setPullTime(date);
            db.setYkzId(ykzOrg.getId());
            db.setId(null);
            ykzUserDbList.add(db);
        });
        this.saveBatch(ykzUserDbList, DbConstant.BATCH_SIZE);
    }
}
