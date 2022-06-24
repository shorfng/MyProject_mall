package com.loto.mall.pay.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.loto.mall.api.pay.model.PayLog;
import com.loto.mall.pay.service.IWeiXinPayService;
import com.loto.mall.util.common.RespResult;
import com.loto.mall.util.security.AESUtil;
import com.loto.mall.util.security.Base64Util;
import com.loto.mall.util.security.MD5;
import com.loto.mall.util.security.Signature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 19:05<p>
 * PageName：WeiXinPayController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/wxPay")
@Api(value = "WeiXinPayController", tags = "微信支付")
@CrossOrigin
public class WeiXinPayController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private IWeiXinPayService weiXinPayService;

    @Autowired
    private Signature signature;

    // 密钥
    @Value("${payConfig.weixin.key}")
    private String skey;

    @ApiOperation(value = "微信支付 - 二维码获取")
    @GetMapping(value = "/pay")
    //public RespResult<Map> pay(@RequestParam Map<String, String> dataMap) throws Exception {
    public RespResult<Map> pay(@RequestParam("ciphertext") String ciphertext) throws Exception {
        // ciphertext -> AES 解密 -> 移除签名数据 signature -> MD5 加密 -> 对比下==signature?
        Map<String, String> dataMap = signature.security(ciphertext);

        // 预支付下单操作（获取支付二维码扫码地址
        Map<String, String> map = weiXinPayService.preOrder(dataMap);

        if (map != null) {
            map.put("orderNumber", dataMap.get("out_trade_no"));  // 订单号
            map.put("money", dataMap.get("total_fee"));           // 支付金额
            return RespResult.ok(map);
        }

        return RespResult.error("支付系统繁忙，请稍后再试！");
    }

    @ApiOperation(value = "微信支付 - 查询订单支付状态")
    @GetMapping(value = "/result/{outTradeNo}")
    public RespResult<PayLog> result(@PathVariable(value = "outTradeNo") String outTradeNo) throws Exception {
        PayLog payLog = weiXinPayService.result(outTradeNo);
        return RespResult.ok(payLog);
    }

    @ApiOperation(value = "支付结果回调")
    @RequestMapping(value = "/result")
    public String result(HttpServletRequest request) throws Exception {
        // 读取网络输入流
        ServletInputStream inputStream = request.getInputStream();

        // 定义接收输入流对象（输出流）
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 将网络输入流读取到输出流中
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        // 关闭资源
        outputStream.close();
        inputStream.close();

        // 将支付结果的 XML 结构，转换成 Map 结构
        String xmlResult = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
        System.out.println("xmlResult:" + xmlResult);

        // 判断支付结果状态（日志状态：2成功，7失败）
        int status = 7;
        // return_code：返回结果
        // result_code：业务结果
        if (map.get("return_code").equals(WXPayConstants.SUCCESS) && map.get("result_code").equals(WXPayConstants.SUCCESS)) {
            status = 2;
        }

        // 创建日志对象
        //PayLog payLog = new PayLog(IdWorker.getIdStr(), 2 , "内容", "订单编号", new Date());
        PayLog payLog = new PayLog(map.get("out_trade_no"), status, JSON.toJSONString(map), map.get("out_trade_no"), new Date());

        // 构建消息
        Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(payLog)).build();

        // 发消息
        rocketMQTemplate.sendMessageInTransaction("rocket", "log", message, null);

        // Map 响应数据
        Map<String, String> resultResp = new HashMap<>();
        resultResp.put("return_code", "SUCCESS");
        resultResp.put("return_msg", "OK");
        return WXPayUtil.mapToXml(resultResp);
    }

    @ApiOperation(value = "申请退款")
    @RequestMapping(value = "/refund/result")
    public String refund(HttpServletRequest request) throws Exception {
        // 读取网络输入流
        ServletInputStream is = request.getInputStream();

        // 定义接收输入流对象（输出流）
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        // 将网络输入流读取到输出流中
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }

        // 关闭资源
        os.close();
        is.close();

        // 将支付结果的 XML 结构，转换成 Map 结构
        String xmlResult = new String(os.toByteArray(), StandardCharsets.UTF_8);
        Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
        System.out.println("退款数据-xmlResult:" + xmlResult);

        // 获取退款信息（使用 AES 加密后的数据）
        String reqInfo = map.get("req_info");
        String key = MD5.md5(skey);  // 密钥需要使用MD5转成小写字母的32位数据（微信支付退款文档写的需要32位）

        // 解密
        byte[] decode = AESUtil.encryptAndDecrypt(Base64Util.decode(reqInfo), key, 2);
        System.out.println("退款解密后的数据：" + new String(decode, StandardCharsets.UTF_8));

        // Map 响应数据
        Map<String, String> resultResp = new HashMap<>();
        resultResp.put("return_code", "SUCCESS");
        resultResp.put("return_msg", "OK");
        return WXPayUtil.mapToXml(resultResp);
    }
}
