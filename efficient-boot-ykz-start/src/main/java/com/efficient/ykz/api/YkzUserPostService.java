package com.efficient.ykz.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.ykz.model.entity.YkzUserPostDb;
import com.efficient.ykz.model.vo.YkzUserPost;

import java.util.List;

/**
 * <p>
 * 愉快政用户职位信息 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
public interface YkzUserPostService extends IService<YkzUserPostDb> {

    void saveErrorMsg(String errorMsg);

    void saveBatchDb(List<YkzUserPost> ykzUserPostList);

}
