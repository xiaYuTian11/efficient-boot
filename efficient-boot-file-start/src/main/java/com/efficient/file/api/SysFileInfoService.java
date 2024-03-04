package com.efficient.file.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.file.model.entity.SysFileInfo;

import java.util.List;

/**
 * @author TMW
 * @since 2022/4/26 9:28
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    SysFileInfo findByPath(String destFile);

    SysFileInfo findByPathAndMd5(String destFile, String md5);

    boolean saveListByBizId(List<String> fileIdList, String bizId);

    List<SysFileInfo> findByBizId(String bizId);
}
