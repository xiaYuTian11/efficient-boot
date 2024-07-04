package com.efficient.file.util;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author TMW
 * @since 2024/2/1 16:01
 */
@Slf4j
public class FileMd5Util {

    /**
     * 使用Java标准库的MessageDigest类获取MD5值
     *
     * @param filePath 文件路径
     * @return 文件的md5值
     */
    public static String calculateMD5(String filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) > 0) {
                    md.update(buffer, 0, read);
                }
            }
            byte[] md5 = md.digest();

            StringBuilder result = new StringBuilder();
            for (byte b : md5) {
                result.append(String.format("%02x", b));
            }

            return result.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("计算文件md5异常", e);
            return null;
        }
    }

    public static String calculateMD5(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = Files.newInputStream(file.toPath())) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) > 0) {
                    md.update(buffer, 0, read);
                }
            }
            byte[] md5 = md.digest();

            StringBuilder result = new StringBuilder();
            for (byte b : md5) {
                result.append(String.format("%02x", b));
            }

            return result.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("计算文件md5异常", e);
            return null;
        }
    }

    /**
     * 使用Java标准库的MessageDigest类获取MD5值
     *
     * @return 文件的md5值
     */
    public static String calculateMD5(MultipartFile file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = file.getInputStream()) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) > 0) {
                    md.update(buffer, 0, read);
                }
            }
            byte[] md5 = md.digest();

            StringBuilder result = new StringBuilder();
            for (byte b : md5) {
                result.append(String.format("%02x", b));
            }

            return result.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("计算文件md5异常", e);
            return null;
        }
    }

    /**
     * 使用Apache Commons Codec库获取MD5值
     *
     * @param filePath 文件路径
     * @return 文件的md5值
     */
    public static String getMD5ByApacheCommonsCodec(String filePath) {
        try {
            return SecureUtil.md5(Files.newInputStream(Paths.get(filePath)));
        } catch (IOException e) {
            log.error("计算文件md5异常", e);
            return null;
        }
    }

    public static String getMD5ByApacheCommonsCodec(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return SecureUtil.md5(inputStream);
        } catch (IOException e) {
            log.error("计算文件md5异常", e);
            return null;
        }
    }

    /**
     * 使用Java NIO获取MD5值
     *
     * @param filePath 文件路径
     * @return 文件的md5值
     */
    public static String getMD5ByNIO(String filePath) {
        try {
            Path path = new File(filePath).toPath();
            try (InputStream is = java.nio.file.Files.newInputStream(path, StandardOpenOption.READ)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) > 0) {
                    md.update(buffer, 0, read);
                }
                byte[] md5 = md.digest();

                StringBuilder result = new StringBuilder();
                for (byte b : md5) {
                    result.append(String.format("%02x", b));
                }

                return result.toString();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("计算文件md5异常", e);
            return null;
        }
    }

}
