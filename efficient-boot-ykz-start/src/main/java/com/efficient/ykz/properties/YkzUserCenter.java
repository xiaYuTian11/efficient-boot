package com.efficient.ykz.properties;

import lombok.Data;

/**
 * 渝快政用户中心
 *
 * @author TMW
 * @since 2024/1/4 11:52
 */
@Data
public class YkzUserCenter {
    /**
     * 用户中心数据处理逻辑，default，custom，需要实现类com.efficient.ykz.api.YkzUserCenterHandleService
     */
    private String handle = "default";
    /**
     * handle 为 custom时必填，全限定名称
     */
    private String handleClassName;
    /**
     * 用户中心 IP
     */
    private String ip = "https://uc-openplatform.bigdatacq.com:4403";
    /**
     * 用户中心 appId
     */
    private String appId;
    /**
     * 用户中心 appSecret
     */
    private String appSecret;
    /**
     * 是否需要入库
     */
    private boolean db = false;
    /**
     * 获取access_token
     */
    private String accessTokenUrl = "/ykz/access_token";
    /**
     * 根据组织机构code查询详细信息
     */
    private String orgByCode = "/ykz/org/getByOrganizationCode";
    /**
     * 根据组织机构code批量查询详细信息
     */
    private String orgByCodeList = "/ykz/org/listByOrganizationCodes";
    /**
     * 根据父级组织机构分页查询子级信息
     */
    private String orgByParentCode = "/ykz/org/pageByParentOrganizationCode";

    /**
     * 根据用户手机号查询用户信息
     */
    private String userByMobile = "/ykz/user/getByMobile";
    /**
     * 批量根据用户手机号查询用户信息
     */
    private String userByMobileList = "/ykz/user/listByMobiles";
    /**
     * 根据用户政钉ID查询用户信息
     */
    private String userByZwddId = "/ykz/user/getByAccountId";
    /**
     * 批量根据用户政钉ID查询用户信息
     */
    private String userByZwddIdList = "/ykz/user/listByAccountIds";
    /**
     * 根据用户ID查询用户职位信息
     */
    private String userPostByZwddId = "/ykz/user/listUserPostByAccountId";
    /**
     * 根据用户政钉Id查询用户标签信息
     */
    private String userTagByZwddId = "/ykz/user/getLabelByAccountId";
    /**
     * 根据用户手机号查询用户标签信息
     */
    private String userTagByMobile = "/ykz/user/getLabelByMobile";
    /**
     * 批量根据用户政钉Id查询用户标签信息
     */
    private String userTagByZwddIdList = "/ykz/user/listLabelByAccountIds";
    /**
     * 批量根据用户手机号查询用户标签信息
     */
    private String userTagByMobileList = "/ykz/user/listLabelByMobiles";
    /**
     * 密码是否加盐
     */
    private boolean enableSalt = true;
    /**
     * 盐值
     */
    private String saltValue = "1809";
}
