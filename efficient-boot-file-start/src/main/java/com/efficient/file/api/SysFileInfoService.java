package com.efficient.file.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.file.model.entity.SysFileInfo;

import java.io.File;
import java.util.List;

/**
 * @author TMW
 * @since 2022/4/26 9:28
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    SysFileInfo findByPathFirst(String destFile);

    SysFileInfo findByPathAndMd5(String destFile, String md5);

    boolean saveListByBizId(List<String> fileIdList, String bizId);

    List<SysFileInfo> findByBizId(String bizId);

    List<SysFileInfo> findByBizIdList(List<String> bizIdList);

    void deleteByBizId(String bizId);

    List<SysFileInfo> findAllByPath(String filePath);

    SysFileInfo findByBizIdAndRemark(String bizId, String remark);

    File getDownPath(String fileName);

    SysFileInfo saveDownFile(File downLoadFile, String bizId,String fileName, String remark);

    SysFileInfo findById(String id);

    SysFileInfo findByPath(String filePath);
}
