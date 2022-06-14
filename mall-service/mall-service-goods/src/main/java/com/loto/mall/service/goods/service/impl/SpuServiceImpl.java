package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.Spu;
import com.loto.mall.service.goods.mapper.SpuMapper;
import com.loto.mall.service.goods.service.ISpuService;
import org.springframework.stereotype.Service;

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

}
