package com.loto.mall.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPay;
import com.loto.mall.api.pay.model.PayLog;
import com.loto.mall.pay.mapper.PayLogMapper;
import com.loto.mall.pay.service.IWeiXinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 12:38<p>
 * PageName：WeiXinPayServiceImpl.java<p>
 * Function：
 */

@Service
public class WeiXinPayServiceImpl implements IWeiXinPayService {
    @Autowired
    private WXPay wxPay;

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * 预支付下单操作（获取支付二维码扫码地址）
     *
     * @param dataMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> preOrder(Map<String, String> dataMap) throws Exception {
        return wxPay.unifiedOrder(dataMap);
    }

    /**
     * 主动查询支付结果
     *
     * @param outTradeNo 订单编号
     * @return
     * @throws Exception
     */
    @Override
    public PayLog result(String outTradeNo) throws Exception {
        // 查询支付日志
        PayLog payLog = payLogMapper.selectById(outTradeNo);

        if (payLog == null) {
            // 数据库中没有数据，查询微信支付服务
            Map<String, String> data = new HashMap<>();
            data.put("out_trade_no", outTradeNo);
            Map<String, String> resp = wxPay.orderQuery(data);

            // 把支付结果存入数据库中（不可逆转的支付结果）
            // trade_state 交易状态 / 支付状态
            String tradeState = resp.get("trade_state");
            int status = tradeState(tradeState);

            // 不可逆转的支付状态，记录日志
            if (status == 2 || status == 3 || status == 4 || status == 5 || status == 7) {
                payLog = new PayLog(outTradeNo, status, JSON.toJSONString(resp), outTradeNo, new Date());
                payLogMapper.insert(payLog);
            }
        }

        return payLog;
    }

    /**
     * 支付状态
     * @param tradeState
     * @return
     */
    public int tradeState(String tradeState) {
        int state = 1;

        switch (tradeState) {
            case "NOTPAY":
                state = 1;  // 未支付
                break;
            case "SUCCESS":
                state = 2;  // 已支付
                break;
            case "REFUND":
                state = 3;  // 转入退款
                break;
            case "CLOSED":
                state = 4;  // 已关闭
                break;
            case "REVOKED":
                state = 5;  // 已撤销
                break;
            case "USERPAYING":
                state = 6;  // 用户支付中
                break;
            case "PAYERROR":
                state = 7;  // 支付失败
                break;
            default:
                state = 1;
        }

        return state;
    }
}
