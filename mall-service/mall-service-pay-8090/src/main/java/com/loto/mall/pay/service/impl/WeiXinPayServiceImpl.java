package com.loto.mall.pay.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.loto.mall.pay.service.IWeiXinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
