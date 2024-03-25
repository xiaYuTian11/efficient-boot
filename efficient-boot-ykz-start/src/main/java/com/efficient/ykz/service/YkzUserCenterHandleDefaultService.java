package com.efficient.ykz.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.result.Result;
import com.efficient.system.api.SysUnitService;
import com.efficient.system.api.SysUserPostService;
import com.efficient.system.api.SysUserService;
import com.efficient.system.constant.SystemConstant;
import com.efficient.system.model.entity.SysUnit;
import com.efficient.system.model.entity.SysUser;
import com.efficient.system.model.entity.SysUserPost;
import com.efficient.ykz.api.YkzUserCenterHandleService;
import com.efficient.ykz.constant.YkzConstant;
import com.efficient.ykz.model.vo.YkzOrg;
import com.efficient.ykz.model.vo.YkzUser;
import com.efficient.ykz.model.vo.YkzUserPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author TMW
 * @since 2024/3/21 11:05
 */
@Slf4j
public class YkzUserCenterHandleDefaultService implements YkzUserCenterHandleService {
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserPostService sysUserPostService;
    @Autowired
    private YkzCommonServer ykzCommonServer;

    private void setChild(YkzOrg ykzOrg) {
        this.handleOrgByCode(ykzOrg);
        List<YkzOrg> children = ykzOrg.getChildren();
        if (CollUtil.isNotEmpty(children)) {
            for (YkzOrg child : children) {
                this.setChild(child);
            }
        }
    }

    private void setSysUser(SysUser sysUser, YkzUser data) {
        sysUser.setId(String.valueOf(data.getId()));
        sysUser.setName(data.getName());
        sysUser.setAccount(data.getUsername());
        sysUser.setPassword(ykzCommonServer.encrypt(SystemConstant.DEFAULT_PASSWORD));
        sysUser.setZwddId(data.getAccountId());
        sysUser.setPhone(data.getMobile());
        sysUser.setIsEnable(CommonConstant.TRUE_INT);
        sysUser.setCreateTime(new Date());
        sysUser.setIsDelete(CommonConstant.FALSE_INT);
        sysUser.setPullTime(new Date());
    }

    @Override
    public Result<YkzOrg> handleOrgByCode(YkzOrg data) {
        Long id = data.getId();
        // 先查找父级
        SysUnit sysUnit = sysUnitService.getById(String.valueOf(id));
        if (Objects.nonNull(sysUnit)) {
            log.info("单位已存在,未入库：名称: {},id: {}", data.getName(), data.getId());
        } else {
            sysUnit = new SysUnit();
            this.setSysUnit(sysUnit, data);
            sysUnitService.save(sysUnit);
        }

        return Result.ok(data);
    }

    @Override
    public Result<List<YkzOrg>> handleOrgByCodeList(List<YkzOrg> data) {
        for (YkzOrg ykzOrg : data) {
            this.handleOrgByCode(ykzOrg);
        }
        return Result.ok(data);
    }

    @Override
    public Result<List<YkzOrg>> handleOrgByParentCode(String orgCode, boolean includeTop, boolean flattenTree, List<YkzOrg> data) {
        if (StrUtil.equalsAny(orgCode, YkzConstant.YKZ_ORG_TOP_CODE_DEV, YkzConstant.YKZ_ORG_TOP_CODE)) {
            // 虚拟顶级节点
            YkzOrg ykzOrg = data.get(0);
            List<YkzOrg> children = ykzOrg.getChildren();
            for (YkzOrg child : children) {
                this.setChild(child);
            }
        } else {
            for (YkzOrg ykzOrg : data) {
                this.setChild(ykzOrg);
            }
        }

        return Result.ok(data);
    }

    @Override
    public Result<YkzUser> handleUserByMobile(YkzUser data) {
        SysUser sysUser = sysUserService.getById(data.getId());
        if (Objects.nonNull(sysUser)) {
            log.info("人员已存在,未入库：名称: {},id: {}", data.getName(), data.getId());
        } else {
            sysUser = new SysUser();
            this.setSysUser(sysUser, data);
            sysUserService.save(sysUser);
        }
        return Result.ok(data);
    }

    @Override
    public Result<List<YkzUser>> handleUserByMobileList(List<YkzUser> data) {
        for (YkzUser ykzUser : data) {
            this.handleUserByMobile(ykzUser);
        }
        return Result.ok(data);
    }

    @Override
    public Result<YkzUser> handleUserByZwddId(YkzUser data) {
        return this.handleUserByMobile(data);
    }

    @Override
    public Result<List<YkzUser>> handleUserByZwddIdList(List<YkzUser> data) {
        return this.handleUserByMobileList(data);
    }

    @Override
    public Result<List<YkzUserPost>> handleUserPostByZwddId(List<YkzUserPost> data) {
        List<SysUserPost> sysUserPostList = new ArrayList<>();
        SysUser sysUser = sysUserService.getByZwddId(data.get(0).getAccountId());
        for (YkzUserPost ykzUserPost : data) {
            String organizationCode = ykzUserPost.getOrganizationCode();
            SysUnit sysUnit = sysUnitService.getByOrgCode(organizationCode);
            if (Objects.isNull(sysUnit)) {
                log.error("人员职务所在单位未入库：orgCode: {}", organizationCode);
                continue;
            }
            String zwddId = ykzUserPost.getAccountId();
            String id = sysUnit.getId() + "_" + zwddId;
            if (Objects.isNull(sysUser)) {
                log.error("人员信息未入库：zwddId: {}", zwddId);
                continue;
            }
            SysUserPost sysUserPost = sysUserPostService.getById(id);
            if (Objects.nonNull(sysUserPost)) {
                log.info("用户职务已存在,未入库：名称: {},id: {}", organizationCode, zwddId);
            } else {
                sysUserPost = new SysUserPost();
                sysUserPost.setId(id);
                sysUserPost.setUserId(sysUser.getId());
                sysUserPost.setDeptId(sysUnit.getId());
                sysUserPost.setDeptLevelCode(sysUnit.getLevelCode());
                SysUnit unitInfo = sysUnitService.getUnitByDeptId(sysUnit.getId());
                if (Objects.nonNull(unitInfo)) {
                    sysUserPost.setUnitId(unitInfo.getId());
                    sysUserPost.setUnitLevelCode(unitInfo.getLevelCode());
                }
                sysUserPost.setPermissionType("1");
                Integer postType = ykzUserPost.getPostType();
                sysUserPost.setMainJob(Objects.equals(postType, 1) ? 1 : 0);
                sysUserPost.setPostName(ykzUserPost.getPosJob());
                sysUserPost.setCreateTime(new Date());
                sysUserPost.setIsDelete(CommonConstant.FALSE_INT);
                sysUserPost.setPullTime(new Date());
                sysUserPostList.add(sysUserPost);
            }
        }
        if (CollUtil.isNotEmpty(sysUserPostList)) {
            SysUserPost userPost = sysUserPostService.getMainByUserId(sysUser.getId());
            if (Objects.isNull(userPost)) {
                sysUserPostList.get(0).setMainJob(CommonConstant.TRUE_INT);
            }
            sysUserPostService.saveOrUpdateBatch(sysUserPostList);
        }
        return Result.ok(data);
    }

    private void setSysUnit(SysUnit sysUnit, YkzOrg data) {
        sysUnit.setId(String.valueOf(data.getId()));
        sysUnit.setParentId(String.valueOf(data.getParentId()));
        sysUnit.setName(data.getName());
        sysUnit.setSortName(data.getGovShortName());
        String levelCode = sysUnitService.createLevelCode(String.valueOf(data.getParentId()));
        sysUnit.setLevelCode(levelCode);
        sysUnit.setUnitType(YkzUserCenterHandleService.getUnitType(data.getOrgType()));
        sysUnit.setSort(data.getDisplayOrder());
        sysUnit.setAddress(data.getGovAddress());
        sysUnit.setGeocode(data.getGovDivisionCode());
        sysUnit.setOrgCode(data.getOrganizationCode());
        sysUnit.setParentOrgCode(data.getParentOrganizationCode());
        sysUnit.setCreditCode(data.getCreditCode());
        sysUnit.setAreaLevel(data.getAreaLevel());
        sysUnit.setPrincipal(data.getPrincipal());
        String belong = sysUnitService.getBelongById(sysUnit.getParentId());
        if (StrUtil.isBlank(belong)) {
            belong = sysUnit.getName();
        } else {
            belong = belong + "-" + sysUnit.getName();
        }
        sysUnit.setBelong(belong);
        sysUnit.setRemark(data.getRemark());
        sysUnit.setIsEnable(data.getIsEnable());
        sysUnit.setCreateTime(new Date(data.getCreateTime()));
        sysUnit.setUpdateTime(new Date(data.getUpdateTime()));
        sysUnit.setIsDelete(data.getIsDeleted());
        sysUnit.setPullTime(new Date());
    }
}
