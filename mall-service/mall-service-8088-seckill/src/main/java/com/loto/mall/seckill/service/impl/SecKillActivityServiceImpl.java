package com.loto.mall.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.seckill.model.SecKillActivity;
import com.loto.mall.seckill.mapper.SecKillActivityMapper;
import com.loto.mall.seckill.service.SecKillActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:45<p>
 * PageName：SecKillActivityServiceImpl.java<p>
 * Function：
 */

@Service
public class SecKillActivityServiceImpl extends ServiceImpl<SecKillActivityMapper, SecKillActivity> implements SecKillActivityService {
    @Autowired
    private SecKillActivityMapper secKillActivityMapper;

    /**
     * 查询有效活动列表
     */
    @Override
    public List<SecKillActivity> validActivity() {
        return secKillActivityMapper.validActivity();
    }
}
