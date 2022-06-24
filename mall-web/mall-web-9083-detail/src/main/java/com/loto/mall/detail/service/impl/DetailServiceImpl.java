package com.loto.mall.detail.service.impl;

import com.alibaba.fastjson.JSON;
import com.loto.mall.api.goods.feign.CategoryFeign;
import com.loto.mall.api.goods.feign.SpuFeign;
import com.loto.mall.api.goods.model.Category;
import com.loto.mall.api.goods.model.Product;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.api.goods.model.Spu;
import com.loto.mall.detail.service.IDetailService;
import com.loto.mall.util.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-20 17:56<p>
 * PageName：DetailServiceImpl.java<p>
 * Function：
 */

@Service
public class DetailServiceImpl implements IDetailService {
    String projectPath = System.getProperty("user.dir");

    @Autowired
    private CategoryFeign categoryFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${generalDetailPagePath}")
    private String generalDetailPagePath;

    /**
     * 生成商品详情的静态页
     *
     * @param spuId
     */
    @Override
    public void html(String spuId) throws Exception {
        // 创建容器对象(上下文对象)
        Context context = new Context();

        // 设置模板数据，数据加载
        context.setVariables(loadData(spuId));

        // 指定文件生成后存储路径
        File file = new File(projectPath + generalDetailPagePath, spuId + ".html");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        // 执行合成生成
        templateEngine.process("item", context, writer);
    }

    /**
     * 数据加载
     */
    public Map<String, Object> loadData(String spuId) {
        // 查询数据
        RespResult<Product> productResult = spuFeign.one(spuId);

        Product product = productResult.getData();
        if (product != null) {
            // Map
            Map<String, Object> resultMap = new HashMap<>();

            // Spu
            Spu spu = product.getSpu();
            resultMap.put("spu", spu);

            // 图片处理
            resultMap.put("images", spu.getImages().split(","));

            // 属性列表
            resultMap.put("attrs", JSON.parseObject(spu.getAttributeList(), Map.class));

            // 三级分类
            RespResult<Category> one = categoryFeign.one(spu.getCategoryOneId());
            RespResult<Category> two = categoryFeign.one(spu.getCategoryTwoId());
            RespResult<Category> three = categoryFeign.one(spu.getCategoryThreeId());
            resultMap.put("one", one.getData());
            resultMap.put("two", two.getData());
            resultMap.put("three", three.getData());

            // Sku 集合
            List<Map<String, Object>> skuList = new ArrayList<>();
            for (Sku sku : product.getSkus()) {
                Map<String, Object> skuMap = new HashMap<>();
                skuMap.put("id", sku.getId());
                skuMap.put("name", sku.getName());
                skuMap.put("price", sku.getPrice());
                skuMap.put("attr", sku.getSkuAttribute());
                // 将 skuMap 添加到 skuList 中
                skuList.add(skuMap);
            }
            resultMap.put("skuList", skuList);

            return resultMap;
        }
        return null;
    }
}
