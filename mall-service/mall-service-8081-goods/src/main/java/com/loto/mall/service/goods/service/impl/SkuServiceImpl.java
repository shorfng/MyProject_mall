package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.cart.model.Cart;
import com.loto.mall.api.goods.model.AdItems;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.service.goods.mapper.AdItemsMapper;
import com.loto.mall.service.goods.mapper.SkuMapper;
import com.loto.mall.service.goods.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@CacheConfig(cacheNames = "ad-items-skus")  // 开启缓存（cacheNames 为缓存的key ）
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {
    @Autowired
    private AdItemsMapper adItemsMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 根据商品推广分类id，查询指定分类下的产品列表
     *
     * @param typeId
     * @return
     */
    //@Cacheable(cacheNames = "ad-items-skus", key = "#typeId")  // 开启缓存
    @Cacheable(key = "#typeId")  // 新增缓存时用（第一次会把数据缓存，后面会先查缓存中有没有，有的话就直接用，没有再查库）
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

    /**
     * 删除缓存（根据商品推广分类id，查询指定分类下的产品列表）
     *
     * @param typeId
     */
    @CacheEvict(key = "#typeId")  // 删除缓存时用
    @Override
    public void delTypeItems(Integer typeId) {

    }

    /**
     * 修改缓存（根据商品推广分类id，查询指定分类下的产品列表）
     *
     * @param typeId
     */
    @CachePut(key = "#typeId")  // 修改缓存时用（每次都把数据存到缓存，但不会使用缓存的数据）
    @Override
    public List<Sku> updateTypeItems(Integer typeId) {
        // 查询当前分类下的所有列表信息
        QueryWrapper<AdItems> adItemsQueryWrapper = new QueryWrapper<>();
        adItemsQueryWrapper.eq("type", typeId);
        List<AdItems> adItems = adItemsMapper.selectList(adItemsQueryWrapper);

        // 根据 adItems 获取所有 SkuId
        List<String> skuIds = adItems.stream().map(AdItems::getSkuId).collect(Collectors.toList());

        // 根据 SkuId 查询产品列表信息（批量查询Sku）
        return skuIds.size() <= 0 ? null : skuMapper.selectBatchIds(skuIds);
    }

    /**
     * 库存递减
     *
     * @param carts
     */
    //@Transactional(rollbackFor = Exception.class)  // 本地事务回滚
    @Override
    public void deleteCount(List<Cart> carts) {
        for (Cart cart : carts) {
            int deleteCount = skuMapper.deleteCount(cart.getSkuId(), cart.getNum());
            if (deleteCount <= 0) {
                throw new RuntimeException("库存不足！");
            }
        }
    }
}
