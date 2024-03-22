package com.efficient.ykz.api;

import com.efficient.common.result.Result;
import com.efficient.ykz.constant.YkzOrgTypeEnum;
import com.efficient.ykz.model.vo.YkzOrg;
import com.efficient.ykz.model.vo.YkzUser;
import com.efficient.ykz.model.vo.YkzUserPost;

import java.util.List;

/**
 * 自定义后续处理
 * @author TMW
 * @since 2024/3/21 10:56
 */
public interface YkzUserCenterHandleService {
    static String getUnitType(String orgType) {
        if (!YkzOrgTypeEnum.GOV_INTERNAL_INSTITUTION.getCode().equals(orgType) && !YkzOrgTypeEnum.GOV_VIRTUAL.getCode().equals(orgType)) {
            return !YkzOrgTypeEnum.GOV_HOLLOW_DIVISION_NODE.getCode().equals(orgType) && !YkzOrgTypeEnum.GOV_HOLLOW_STRIP_NODE.getCode().equals(orgType) ? "2" : "1";
        } else {
            return "3";
        }
    }

    Result<YkzOrg> handleOrgByCode(YkzOrg data);

    Result<List<YkzOrg>> handleOrgByCodeList(List<YkzOrg> data);

    /**
     *
     * @param orgCode  父级
     * @param includeTop 是否包含父级
     * @param flattenTree 是否集合形式，默认树形结构
     * @param data 数据集合
     * @return
     */
    Result<List<YkzOrg>> handleOrgByParentCode(String orgCode, boolean includeTop, boolean flattenTree, List<YkzOrg> data);

    Result<YkzUser> handleUserByMobile(YkzUser data);

    Result<List<YkzUser>> handleUserByMobileList(List<YkzUser> data);

    Result<YkzUser> handleUserByZwddId(YkzUser data);

    Result<List<YkzUser>> handleUserByZwddIdList(List<YkzUser> data);

    Result<List<YkzUserPost>> handleUserPostByZwddId(List<YkzUserPost> data);
}
