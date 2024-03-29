package com.efficient.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.file.model.entity.SysFileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件信息 持久层
 * </p>
 *
 * @author code generator
 * @date 2022-08-26 11:21:03
 */
@Mapper
public interface SysFileInfoMapper extends BaseMapper<SysFileInfo> {

    void deleteByFIleIdListAndBizId(@Param("fileIdList") List<String> fileIdList, @Param("bizId") String bizId);

    void setBizIdWithFileIdList(@Param("fileIdList") List<String> fileIdList, @Param("bizId") String bizId);

    void deleteByBizId(@Param("bizId") String bizId);
}
