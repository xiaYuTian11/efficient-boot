package com.efficient.file.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;

import java.io.File;
import java.util.List;

/**
 * @author TMW
 * @since 2022/4/26 9:28
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    SysFileInfo findByPathFirst(String destFile);

    SysFileInfo findByPathAndMd5(String destFile, String md5);

    boolean saveIdListByBizId(List<String> fileIdList, String bizId);

    boolean saveFileListByBizId(List<FileVO> fileList, String bizId);

    boolean saveStrListByBizId(String fileIdStr, String bizId);

    List<SysFileInfo> findByBizId(String bizId);

    List<FileVO> findVOByBizId(String bizId);

    List<SysFileInfo> findByBizIdList(List<String> bizIdList);

    List<FileVO> findVOByBizIdList(List<String> bizIdList);

    void deleteByBizId(String bizId);

    List<SysFileInfo> findAllByPath(String filePath);

    SysFileInfo findByBizIdAndRemark(String bizId, String remark);

    File getDownPath(String fileName);

    FileVO createTempByTemplate(String TemplateClassPath, String fileName) throws Exception;

    SysFileInfo saveDownFile(File downLoadFile, String bizId, String fileName, String remark);

    SysFileInfo findById(String id);

    SysFileInfo findByPath(String filePath);
}
