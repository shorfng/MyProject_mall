package com.loto.mall.util.security;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 13:27<p>
 * PageName：Signature.java<p>
 * Function：验签工具类
 */

public class Signature {
    // 秘钥
    private String skey;

    // 验签加盐值
    private String salt;

    public Signature(String skey, String salt) {
        this.skey = skey;
        this.salt = salt;
    }

    // 测试
    public static void main(String[] args) throws Exception {
        String skey = "ab2cc473d3334c39";
        String salt = "XPYQZb1kMES8HNaJWW8+TDu/4JdBK4owsU9eXCXZDOI=";

        // 需要加密的字串
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("body", "商城订单-");
        dataMap.put("out_trade_no", "AAA");
        dataMap.put("device_info", "PC");
        dataMap.put("fee_type", "CNY");
        dataMap.put("total_fee", "1");
        dataMap.put("spbill_create_ip", "192.168.100.130");
        dataMap.put("notify_url", "http://www.example.com/wxpay/notify");
        dataMap.put("trade_type", "NATIVE");  // 此处指定为扫码支付

        Signature signature = new Signature(skey, salt);

        // 加密
        //String cSrc = "5Ei1DggFVRPJ7Lses+ox3S40NV4dlYIuKSwRhvEbuDWlGKPj92pQOf/yvbAxKdmj1LCc8n6YRhMdlPDFYfx4FMPW5LHLCoEhmBQBtvb7ia8IWcHnMGPX9sX5eaOLolzeURLhxGVJZ8GKvTIPuaLW0mmaJmIrI1bSL6did6h0QAe5SNnQJyORRydtPEXp5nPteiUT8cW4mA2MNiQyUNG/+cCzVdBVPwkNIrh0WU2gqgcYbuCuBJMNvt3a/lpBzkCf0zlwoA5GWiAFZ6FpBtwR/Mj8rkmqe7IXnWjPv2dSMwbSFhZAXGmG8dUsYj/u7iNLzfVBcncbG5ZEWdw0+IXFEThTLJbA26kdrt4f58zRHkrRg9y7A6uVyDvduXBw0743ZXuJgI2a9pt2vq4CRY2/xA==";
        String cSrc = signature.security(dataMap);
        System.out.println(cSrc);

        // 解密
        Map<String, String> map1 = signature.security(cSrc);
        System.out.println(map1);
    }

    /**
     * Map 加密，携带验签
     */
    public String security(Map<String, String> dataMap) throws Exception {
        // 将 dataMap 转成 TreeMap
        dataMap = JSON.parseObject(JSON.toJSONString(dataMap), TreeMap.class);

        // 将 TreeMap 转成 JSON
        String treeJson = JSON.toJSONString(dataMap);

        // 执行 MD5 摘要加密
        String signature = MD5.md5(treeJson, salt);

        // 摘要加密内容添加到 dataMap 中
        dataMap.put("signature", signature);

        // AES加密 dataMap
        return Base64Util.encodeURL(AESUtil.encryptAndDecrypt(JSON.toJSONString(dataMap).getBytes(StandardCharsets.UTF_8), skey, 1));
    }

    /**
     * 密文解密，并转成 Map，并对Map数据进行验签
     *
     * @param ciphertext
     * @return
     */
    public Map<String, String> security(String ciphertext) throws Exception {
        // 解密
        String decrypt = new String(AESUtil.encryptAndDecrypt(Base64Util.decodeURL(ciphertext), skey, 2), StandardCharsets.UTF_8);

        // 明文转 Map，并根据 Key 降序
        Map<String, String> decryptTreeMap = JSON.parseObject(decrypt, TreeMap.class);

        // 验签
        String signature = decryptTreeMap.remove("signature");
        String localSignature = MD5.md5(JSON.toJSONString(decryptTreeMap), salt);

        // true 验签成功，false 验签失败
        return signature.equals(localSignature) ? decryptTreeMap : null;
    }
}
