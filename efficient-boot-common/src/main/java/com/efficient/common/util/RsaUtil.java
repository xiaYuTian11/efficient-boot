package com.efficient.common.util;

import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.efficient.common.entity.KeyPair;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author TMW
 * @since 2024/4/16 17:07
 */
public class RsaUtil {

    /**
     * 私钥加密
     *
     * @param data         base64加密串
     * @param publicKeyStr 公钥
     * @return 解密内容
     */
    public static String encrypt(String data, String publicKeyStr) {
        RSA rsa = new RSA(null, publicKeyStr);
        byte[] encrypt = rsa.encrypt(data.getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
        return Base64.getEncoder().encodeToString(encrypt);

    }

    /**
     * 公钥解密
     *
     * @param data          数据
     * @param privateKeyStr 私钥
     * @return base64加密串
     */
    public static String decrypt(String data, String privateKeyStr) {
        RSA rsa = new RSA(privateKeyStr, null);
        byte[] decrypt = rsa.decrypt(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8)), KeyType.PrivateKey);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    /**
     * 私钥加密
     *
     * @param data          base64加密串
     * @param privateKeyStr 私钥
     * @return 解密内容
     */
    public static String encryptReversal(String data, String privateKeyStr) {
        RSA rsa = new RSA(privateKeyStr, null);
        byte[] encrypt = rsa.encrypt(data.getBytes(StandardCharsets.UTF_8), KeyType.PrivateKey);
        return Base64.getEncoder().encodeToString(encrypt);
    }

    /**
     * 公钥解密
     *
     * @param data         数据
     * @param publicKeyStr 公钥
     * @return base64加密串
     */
    public static String decryptReversal(String data, String publicKeyStr) {
        RSA rsa = new RSA(null, publicKeyStr);
        byte[] decrypt = rsa.decrypt(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8)), KeyType.PublicKey);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println(RsaUtil.decryptReversal("n5ECLsh8lZNBeUwFNbilO6DG6PPXOESXwdp8m7HC1YgIg2D8Z8bU1qlg5LcPdrpBdZvRlur0dQLXIUnovm4cP7cAT0oMSXuhtF4VeuhhJ1tKLf+AqivWX/YXO7EtbhKVqYZxbnuFncjMA71TgpR0rElizTm1abbQ3DvXLeg2ZtI=", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJxB8TcseHJ+l+pcgDtZcKp4RX0NPn0hJfxRkepszyxxnX2VomB+9UvU8XOyTyvtVTANCPmWbn9g2KaonhcVNUJPcKPMFpJShXXReibI9gDs1wO7+KzWe+btCAngf90t9sl9U9O7Nhf/iVaaTr2jikQNNqpsjHrUTu2PcufS5UyQIDAQAB"));
    }

    /**
     * 生成密钥对
     *
     * @return 密钥对
     */
    public static KeyPair generateKeyPair() {
        // 使用 RSA 算法生成密钥对，指定密钥长度为2048位
        AsymmetricCrypto rsa = new AsymmetricCrypto(AsymmetricAlgorithm.RSA);
        // 生成公钥和私钥
        byte[] publicKey = rsa.getPublicKey().getEncoded();
        byte[] privateKey = rsa.getPrivateKey().getEncoded();

        // 将公钥和私钥转换成Base64编码的字符串
        String publicKeyStr = cn.hutool.core.codec.Base64.encode(publicKey);
        String privateKeyStr = cn.hutool.core.codec.Base64.encode(privateKey);

        return KeyPair.builder().publicKey(publicKeyStr).privateKey(privateKeyStr).build();
    }
}
