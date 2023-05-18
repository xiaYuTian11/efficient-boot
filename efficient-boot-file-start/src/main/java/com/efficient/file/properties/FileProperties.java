package com.efficient.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/8/26 9:59
 */
@ConfigurationProperties("com.efficient.file")
@Data
public class FileProperties {
    public static final String DEFAULT_PATH = "/efficient/file/";
    private String active = "local";
    private String tempPath = DEFAULT_PATH + "temp/";
    private FileProperties.Local local = new FileProperties.Local();

    @Data
    public static class Local {
        private String localPath = DEFAULT_PATH;
        /**
         * 文件路径添加日期前缀
         */
        private boolean addDatePrefix = true;
    }

}
