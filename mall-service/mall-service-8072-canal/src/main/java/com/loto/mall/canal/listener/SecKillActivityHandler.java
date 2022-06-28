package com.loto.mall.canal.listener;

import com.loto.mall.api.seckill.model.SecKillActivity;
import com.loto.mall.canal.job.dynamicjob.DynamicJob;
import com.loto.mall.canal.job.dynamicjob.DynamicTaskCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

import java.text.SimpleDateFormat;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-28 11:37<p>
 * PageName：SecKillActivityHandler.java<p>
 * Function：
 */

@Component
@CanalTable(value = "seckill_activity")
public class SecKillActivityHandler implements EntryHandler<SecKillActivity> {
    @Autowired
    private DynamicTaskCreate dynamicTaskCreate;

    /**
     * 增加活动
     *
     * @param secKillActivity
     */
    @Override
    public void insert(SecKillActivity secKillActivity) {
        // 创建任务调度，活动结束的时候执行
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String cron = simpleDateFormat.format(secKillActivity.getEndTime());
        System.out.println("cron:" + cron);

        dynamicTaskCreate.create(secKillActivity.getId(), cron, 1, new DynamicJob(), secKillActivity.getId());
    }

    /**
     * 更新活动
     *
     * @param before
     * @param after
     */
    @Override
    public void update(SecKillActivity before, SecKillActivity after) {
        // 创建任务调度，活动结束的时候执行
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String cron = simpleDateFormat.format(after.getEndTime());
        System.out.println("cron:" + cron);

        dynamicTaskCreate.create(after.getId(), cron, 1, new DynamicJob(), after.getId());
    }

    /**
     * 删除活动
     *
     * @param secKillActivity
     */
    @Override
    public void delete(SecKillActivity secKillActivity) {

    }
}
