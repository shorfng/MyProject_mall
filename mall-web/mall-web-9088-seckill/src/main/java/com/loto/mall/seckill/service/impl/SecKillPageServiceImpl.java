package com.loto.mall.seckill.service.impl;

import com.loto.mall.api.seckill.feign.SecKillGoodsFeign;
import com.loto.mall.api.seckill.model.SecKillGoods;
import com.loto.mall.seckill.service.SecKillPageService;
import com.loto.mall.util.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:51<p>
 * PageName：SecKillPageServiceImpl.java<p>
 * Function：
 */

@Service
public class SecKillPageServiceImpl implements SecKillPageService {
    String projectPath = System.getProperty("user.dir");

    @Value("${secKillPagePath}")
    private String secKillPagePath;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SecKillGoodsFeign secKillGoodsFeign;

    /**
     * 秒杀详情页 - 生成
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void html(String id) throws Exception {
        // 创建容器对象(上下文对象)
        Context context = new Context();

        // 设置模板数据，数据加载
        context.setVariables(loadData(id));

        // 指定文件生成后存储路径
        File file = new File(projectPath + secKillPagePath, id + ".html");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        // 执行合成生成
        templateEngine.process("seckillitem", context, writer);
    }

    /**
     * 数据加载
     */
    public Map<String, Object> loadData(String id) {
        // 查询数据
        RespResult<SecKillGoods> goodsResp = secKillGoodsFeign.one(id);
        if (goodsResp.getData() != null) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("item", goodsResp.getData());
            dataMap.put("images", goodsResp.getData().getImages().split(","));
            return dataMap;
        }

        return null;
    }
}
