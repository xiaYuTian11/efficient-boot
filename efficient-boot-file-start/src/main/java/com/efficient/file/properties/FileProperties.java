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

    private String active = "local";
    private String path;
    private FileProperties.Local local = new FileProperties.Local();

    @Data
    public static class Local {
        private String localPath = "/efficient/file/";
    }

}
