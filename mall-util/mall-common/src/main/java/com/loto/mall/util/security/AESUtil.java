package com.loto.mall.util.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-23 18:11<p>
 * PageName：AESUtil.java<p>
 * Function：
 */

public class AESUtil {
    /**
     * AES加密/解密
     *
     * @param buffer    密文/明文
     * @param appSecret 秘钥
     * @param mode      加密/解密模式（1加密 2解密）
     */
    public static byte[] encryptAndDecrypt(byte[] buffer, String appSecret, Integer mode) throws Exception {
        // 加载加密解密算法处理对象（包含算法、秘钥管理）
        Security.addProvider(new BouncyCastleProvider());

        // 根据不同算法创建秘钥
        // 参数1：秘钥的字节数组
        // 参数2：加密算法
        SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "AES");

        // 设置加密模式（无论是加密还是解析，模式一致）
        // 参数1：设置算法
        // 参数2：指定算法库对象
        // 微信支付使用该算法：AES/ECB/PKCS7Padding（算法/加密模式/填充模式）
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");

        // 初始化加密配置
        cipher.init(mode, secretKeySpec);

        // 执行加密/解密
        return cipher.doFinal(buffer);
    }

    /**
     * 加密解密测试
     */
    public static void main(String[] args) throws Exception {
        //Integer mode = 1;   // 1加密 2解密
        //String txt = "SpringCloud Alibaba";     // 明文
        //String appSecret = "aaaaaaaaaaaaaaaa";  // 密钥（可以使用 128/192/256 bite） 128bite 对应16位
        //
        //// 加密
        //byte[] bytes = encryptAndDecrypt(txt.getBytes(StandardCharsets.UTF_8), appSecret, mode);
        //String encode = Base64Util.encode(bytes);
        //System.out.println("加密后：" + encode);
        //
        //// 解密（先使用 Base64 解码，再使用 AES 解密）
        //byte[] decode = encryptAndDecrypt(Base64Util.decode(encode), appSecret, 2);
        //System.out.println(new String(decode, StandardCharsets.UTF_8));

        Integer mode = 1;   // 1加密 2解密
        String txt = "SpringCloud Alibaba";     // 明文
        String appSecret = "aaaaaaaaaaaaaaaa";  // 密钥（可以使用 128/192/256 bite） 128bite 对应16位

        // 对密钥md5加密，加密的结果字母都为小写
        appSecret = MD5.md5(appSecret);
        System.out.println("对密钥md5加密，加密的结果字母都为小写：" + appSecret);

        // 加密
        byte[] bytes = encryptAndDecrypt(txt.getBytes(StandardCharsets.UTF_8), appSecret, mode);
        String encode = Base64Util.encode(bytes);
        System.out.println("加密后：" + encode);

        // 解密（先使用 Base64 解码，再使用 AES 解密）
        byte[] decode = encryptAndDecrypt(Base64Util.decode(encode), appSecret, 2);
        System.out.println(new String(decode, StandardCharsets.UTF_8));
    }
}
