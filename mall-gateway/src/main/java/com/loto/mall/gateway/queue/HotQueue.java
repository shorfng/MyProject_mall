package com.loto.mall.gateway.queue;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 20:48<p>
 * PageName：HotQueue.java<p>
 * Function：热门商品排队操作
 */

@Component
public class HotQueue {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static final Integer NOT_HOT = 0;     // 状态码，表示商品非热门
    public static final Integer HAS_QUEUE = 204; // 状态码，表示已经在排队中
    public static final Integer QUEUE_ING = 200; // 状态码，表示排队成功

    /**
     * 抢单排队
     *
     * @param username 用户名
     * @param id       商品ID
     * @param num      件数
     * @return
     */
    public int hotToQueue(String username, String id, Integer num) {
        // 获取该商品在Redis中的信息，如果Redis中存在对应信息，热门商品
        Boolean bo = redisTemplate.boundHashOps("HotSeckillGoods").hasKey(id);

        if (!bo) {
            // 商品非热门
            return NOT_HOT;
        }

        // 避免重复排队
        Long increment = redisTemplate.boundValueOps("OrderQueue" + username).increment(1);
        if (increment > 1) {
            // 请勿重新排队
            return HAS_QUEUE;
        }

        // 2分钟过期
        redisTemplate.boundValueOps("OrderQueue" + username).expire(2, TimeUnit.MINUTES);

        // 执行排队操作
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", username);
        dataMap.put("id", id);
        dataMap.put("num", num);
        Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(dataMap)).build();
        rocketMQTemplate.convertAndSend("order-queue", message);

        return QUEUE_ING;
    }
}
