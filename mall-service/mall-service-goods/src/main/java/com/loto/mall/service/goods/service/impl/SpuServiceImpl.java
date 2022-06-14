package com.loto.mall.service.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.Brand;
import com.loto.mall.api.goods.model.Category;
import com.loto.mall.api.goods.model.Product;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.api.goods.model.Spu;
import com.loto.mall.service.goods.mapper.BrandMapper;
import com.loto.mall.service.goods.mapper.CategoryMapper;
import com.loto.mall.service.goods.mapper.SkuMapper;
import com.loto.mall.service.goods.mapper.SpuMapper;
import com.loto.mall.service.goods.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 商品表（同类产品信息） 服务实现类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ISpuService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 保存同类产品信息
     *
     * @param product
     */
    @Override
    public void saveProduct(Product product) {
        // 1、保存Spu
        Spu spu = product.getSpu();

        if (StringUtils.isEmpty(spu.getId())) {
            spu.setIsMarketable(1); // 已上架
            spu.setIsDelete(0);     // 未删除
            spu.setStatus(1);       // 审核已通过

            // 新增
            spuMapper.insert(spu);
        } else {
            // 修改
            spuMapper.updateById(spu);
            // 删除Sku集合
            skuMapper.delete(new QueryWrapper<Sku>().eq("spu_id", spu.getId()));
        }

        // 2、保存List<Sku>
        Date date = new Date();
        Category category = categoryMapper.selectById(spu.getCategoryThreeId());
        Brand brand = brandMapper.selectById(spu.getBrandId());

        for (Sku sku : product.getSkus()) {
            // SKU名称
            // {"适合人群":"有一定java基础的人","书籍分类":"科技"}
            String name = spu.getName();
            Map<String, String> skuAttrMap = JSON.parseObject(sku.getSkuAttribute(), Map.class);
            for (Map.Entry<String, String> entry : skuAttrMap.entrySet()) {
                name += "  " + entry.getValue();
            }
            sku.setName(name);

            sku.setCreateTime(date);  // 创建时间
            sku.setUpdateTime(date);  // 修改时间
            sku.setCategoryId(spu.getCategoryThreeId());  // 分类ID
            sku.setBrandName(brand.getName()); // 分类名字
            sku.setBrandId(spu.getBrandId());  // 品牌ID
            sku.setCategoryName(category.getName());      // 品牌名字
            sku.setSpuId(spu.getId()); // spuid
            sku.setStatus(1);          // 商品状态（1-正常，2-下架，3-删除）

            // 添加
            skuMapper.insert(sku);
        }
    }
}
