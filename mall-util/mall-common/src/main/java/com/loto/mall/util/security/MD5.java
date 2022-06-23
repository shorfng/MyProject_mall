package com.loto.mall.util.security;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
    /**
     * MD5 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        // 加密后的字符串
        return DigestUtils.md5Hex(text);
    }

    /**
     * MD5 加密 - 加盐
     *
     * @param text 明文
     * @param key  盐
     * @return 密文
     */
    public static String md5(String text, String key) {
        // 加密后的字符串
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * MD5 验证方法
     *
     * @param text 明文
     * @param key  密钥/盐
     * @param md5  密文
     * @return true/false
     */
    public static boolean verify(String text, String key, String md5) {
        // 根据传入的密钥进行验证
        String md5Text = md5(text, key);
        return md5Text.equalsIgnoreCase(md5);
    }
}
