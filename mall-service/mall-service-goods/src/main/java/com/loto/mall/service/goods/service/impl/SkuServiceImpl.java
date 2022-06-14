package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.Sku;
import com.loto.mall.service.goods.mapper.SkuMapper;
import com.loto.mall.service.goods.service.ISkuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表（每个产品信息） 服务实现类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {

}
