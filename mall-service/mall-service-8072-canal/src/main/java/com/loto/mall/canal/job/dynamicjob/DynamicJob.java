package com.loto.mall.canal.job.dynamicjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.loto.mall.api.seckill.feign.SecKillPageFeign;
import com.loto.mall.canal.spring.SpringContext;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-28 11:25<p>
 * PageName：DynamicJob.java<p>
 * Function：
 */

public class DynamicJob implements SimpleJob {
    /**
     * 执行的作业
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        // 静态页删除
        delete(shardingContext.getJobParameter());

        System.out.println("静态页删除完毕！");
    }

    /**
     * 执行静态页删除
     */
    public void delete(String acid) {
        // 从容器中获取指定的实例
        SecKillPageFeign secKillPageFeign = SpringContext.getBean(SecKillPageFeign.class);
        secKillPageFeign.deleByAct(acid);
    }
}

