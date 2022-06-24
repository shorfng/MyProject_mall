package com.loto.mall.pay.service;

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

}
