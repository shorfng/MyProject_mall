package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.AdItems;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.service.goods.mapper.AdItemsMapper;
import com.loto.mall.service.goods.mapper.SkuMapper;
import com.loto.mall.service.goods.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品表（每个产品信息） 服务实现类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Service
@CacheConfig(cacheNames = "ad-items-skus")  // 开启缓存
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {
    @Autowired
    private AdItemsMapper adItemsMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 根据商品推广分类id，查询指定分类下的产品列表
     *
     * @param id
     * @return
     */
    //@Cacheable(cacheNames = "ad-items-skus", key = "#typeId")  // 开启缓存
    @Cacheable(key = "#typeId")
    @Override
    public List<Sku> typeSkuItems(Integer typeId) {
        // 查询当前分类下的所有列表信息
        QueryWrapper<AdItems> adItemsQueryWrapper = new QueryWrapper<>();
        adItemsQueryWrapper.eq("type", typeId);
        List<AdItems> adItems = adItemsMapper.selectList(adItemsQueryWrapper);

        // 根据 adItems 获取所有 SkuId
        List<String> skuIds = adItems.stream().map(AdItems::getSkuId).collect(Collectors.toList());

        // 根据 SkuId 查询产品列表信息（批量查询Sku）
        return skuIds.size() <= 0 ? null : skuMapper.selectBatchIds(skuIds);
    }
}
