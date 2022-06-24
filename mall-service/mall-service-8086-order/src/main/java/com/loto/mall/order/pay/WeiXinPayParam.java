package com.loto.mall.order.pay;

import com.loto.mall.api.order.model.Order;
import com.loto.mall.util.security.Signature;
import com.loto.mall.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-24 13:41<p>
 * PageName：WeiXinPayParam.java<p>
 * Function：
 */

@Component
public class WeiXinPayParam {
    @Autowired
    private Signature signature;

    /**
     * 支付数据处理
     */
    public String weiXinParam(Order order, HttpServletRequest request) throws Exception {
        // 预支付下单需要用到的数据
        Map<String, String> data = new HashMap<>();
        data.put("body", "SpringCloud Alibaba商城");
        data.put("out_trade_no", order.getId());    // 订单号
        data.put("device_info", "PC");  // 设备信息
        data.put("fee_type", "CNY");    // 币种
        //data.put("total_fee", String.valueOf(order.getMoneys()*100));     // 支付金额
        data.put("total_fee","1");     // 支付金额（测试固定为1分钱）
        data.put("spbill_create_ip", IPUtils.getIpAddr(request));  // 客户端IP
        data.put("notify_url", "http://2cw4969042.wicp.vip:48847/wxPay/result");  // 回调地址（支付结果通知地址）（ip和端口号可以用过花生壳软件生成）
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付

        // 将 data -> 转成 TreeMap -> MD5 加密 -> 存到 Map -> 转成 JSON -> AES 加密
        return signature.security(data);
    }
}
