package com.loto.mall.canal.job.staticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 19:17<p>
 * PageName：StaticJobTask.java<p>
 * Function：
 */

// 参数1：jobName（指定 Zookeeper 中的命名空间）
// 参数2：shardingTotalCount（分片）
// 参数3：cron（执行周期）
//@ElasticSimpleJob(jobName = "${elaticjob.zookeeper.namespace}", shardingTotalCount = 1, cron = "0/5 * * * * ? *")
//@Component
public class StaticJobTask implements SimpleJob {
    // 执行的作业
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("StaticJobTask-----------execute");
    }
}
