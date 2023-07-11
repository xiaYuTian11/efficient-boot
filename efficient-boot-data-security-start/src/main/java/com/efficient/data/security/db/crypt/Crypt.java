package com.efficient.data.security.db.crypt;

/**
 * 加密接口，自定义算法必须实现此接口
 *
 * @author TMW
 * @since 2023/7/10 15:39
 */
public interface Crypt {
    /**
     * 加密数据
     *
     * @param content 明文
     * @return 密文
     */
    String encrypt(String content);

    /**
     * 加密数据
     *
     * @param content 密文
     * @return 明文
     */
    String decrypt(String content);
}
