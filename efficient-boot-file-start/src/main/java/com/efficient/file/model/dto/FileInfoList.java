package com.efficient.file.model.dto;

import com.efficient.file.model.vo.FileVO;
import lombok.Data;

import java.util.List;

/**
 * 绑定文件关系
 *
 * @author TMW
 * @since 2024/3/4 13:51
 */
@Data
public class FileInfoList {
    private String bizId;
    private List<FileVO> fileList;
}
