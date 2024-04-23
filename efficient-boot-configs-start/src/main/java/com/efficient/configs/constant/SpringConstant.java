package com.efficient.configs.constant;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @author TMW
 * @since 2024/4/23 9:51
 */
public class SpringConstant {
    public static String CLASS_PATH;
    public static String TEMPLATES_PATH;
    public static String STATIC_PATH;
    public static String PUBLIC_PATH;

    static {
        try {
            CLASS_PATH = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TEMPLATES_PATH = CLASS_PATH + "/templates/";
        STATIC_PATH = CLASS_PATH + "/static/";
        PUBLIC_PATH = CLASS_PATH + "/public/";
    }
}
