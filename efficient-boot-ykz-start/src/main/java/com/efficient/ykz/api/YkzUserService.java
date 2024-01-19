package com.efficient.ykz.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.ykz.model.entity.YkzUserDb;
import com.efficient.ykz.model.vo.YkzUser;

import java.util.List;

/**
 * <p>
 * 渝快政用户信息 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
public interface YkzUserService extends IService<YkzUserDb> {

    void saveErrorMsg(String errorMsg);

    void saveOne(YkzUser ykzUser);

    void saveBatchDb(List<YkzUser> ykzUserList);
}
