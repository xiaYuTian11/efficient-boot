package com.efficient.file.model.vo;

import com.efficient.file.model.entity.SysFileInfo;
import lombok.Data;

import java.util.List;

/**
 * 绑定文件关系
 *
 * @author TMW
 * @since 2024/3/4 13:51
 */
@Data
public class FileBizRelationVO {
    private List<SysFileInfo> fileInfoList;

}
