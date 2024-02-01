package com.efficient.file.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.common.result.Result;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.util.PathUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

import static com.efficient.file.constant.FileConstant.DOWNLOAD_LINE;
import static com.efficient.file.constant.FileConstant.POINT;

/**
 * @author TMW
 * @since 2022/4/26 9:28
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    SysFileInfo findByPath(String destFile);
}
