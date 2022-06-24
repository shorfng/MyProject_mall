package com.loto.mall.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.pay.model.PayLog;
import com.loto.mall.pay.mapper.PayLogMapper;
import com.loto.mall.pay.service.IPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-22 19:01<p>
 * PageName：PayLogServiceImpl.java<p>
 * Function：
 */

@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements IPayLogService {
    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * 记录日志
     * @param payLog
     */
    @Override
    public void add(PayLog payLog) {
        // 删除
        payLogMapper.deleteById(payLog.getId());

        // 增加
        payLogMapper.insert(payLog);
    }
}
