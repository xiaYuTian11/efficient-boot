package com.efficient.ykz.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.ykz.model.entity.YkzOrgDb;
import com.efficient.ykz.model.vo.YkzOrg;

import java.util.List;

/**
 * <p>
 * efficient_ykz_org 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
public interface YkzOrgService extends IService<YkzOrgDb> {

    void saveErrorMsg(String errorMsg);

    void saveOne(YkzOrg ykzOrg);

    void saveBatchDb(List<YkzOrg> resultList);
}
