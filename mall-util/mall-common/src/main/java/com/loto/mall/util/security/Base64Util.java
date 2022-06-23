package com.loto.mall.util.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {
    public static void main(String[] args) throws Exception {
        String str = "测试数据";

        // 普通的加密解密
        String encode = encode(str.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密后：" + encode);

        byte[] decode = decode(encode);
        System.out.println(new String(decode, StandardCharsets.UTF_8));

        // 用于URL的改进Base64编码，加密解密
        String s = encodeURL(str.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密后：" + s);

        byte[] bytes = decodeURL(s);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

    }

    /**
     * 普通加密
     *
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        final Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    /**
     * 普通解密
     *
     * @param encodedText：密文
     * @return
     */
    public static byte[] decode(String encodedText) {
        final Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(encodedText);
    }

    /**
     * 加密操作
     *
     * @param data
     * @return
     */
    public static String encodeURL(byte[] data) {
        final Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(data);
    }

    /**
     * 解密操作
     *
     * @param encodedText
     * @return
     */
    public static byte[] decodeURL(String encodedText) {
        final Base64.Decoder decoder = Base64.getUrlDecoder();
        return decoder.decode(encodedText);
    }

}
