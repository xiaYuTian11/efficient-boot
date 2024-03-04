package com.efficient.file.api;

import com.efficient.common.result.Result;
import com.efficient.file.model.dto.FileChunkDTO;
import com.efficient.file.model.entity.SysFileInfo;

/**
 *
 * @author TMW
 * @since 2024/1/29 11:13
 */
public interface VideoService {

    Result<SysFileInfo> chunkUpload(FileChunkDTO fileChunkDTO) throws Exception;

    Result checkFile(String module, String md5) throws Exception;

    Result quickUpload(String module, String md5, String fileName, String remark);
}
