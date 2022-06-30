package com.loto.mall.seckill.controller;

import com.loto.mall.util.common.RespResult;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 16:23<p>
 * PageName：RedissonLockTaskController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/test/lock")
public class RedissonLockTaskController {
    @Value("${server.port}")
    private String port;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping
    public RespResult doOrder() throws InterruptedException {
        // 获取锁
        RLock lock = redissonClient.getLock("No00001");
        lock.lock();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("获取锁:" + port + "，时间：" + simpleDateFormat.format(new Date()));

        // 5s后释放锁
        TimeUnit.SECONDS.sleep(5);
        System.out.println("释放锁:" + port + "，时间：" + simpleDateFormat.format(new Date()));
        lock.unlock();

        return RespResult.ok(port);
    }
}
