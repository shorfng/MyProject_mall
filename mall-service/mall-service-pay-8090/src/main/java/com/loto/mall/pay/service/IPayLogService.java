package com.loto.mall.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.pay.model.PayLog;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 19:00<p>
 * PageName：IPayLogService.java<p>
 * Function：
 */

public interface IPayLogService extends IService<PayLog> {
    /**
     * 记录日志
     * @param payLog
     */
    void add(PayLog payLog);
}
