package com.loto.mall.pay.service;

import com.loto.mall.api.pay.model.PayLog;

import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 12:38<p>
 * PageName：IWeiXinPayService.java<p>
 * Function：
 */

public interface IWeiXinPayService {
    /**
     * 预支付下单操作（获取支付二维码扫码地址）
     * @param dataMap
     * @return
     * @throws Exception
     */
    Map<String,String> preOrder(Map<String,String> dataMap) throws Exception;

    /**
     * 主动查询支付结果
     * @param outno 订单编号
     * @return
     * @throws Exception
     */
    PayLog result(String outTradeNo) throws Exception;
}
