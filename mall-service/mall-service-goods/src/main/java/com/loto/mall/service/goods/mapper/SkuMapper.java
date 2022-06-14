package com.loto.mall.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.goods.model.Sku;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品表（每个产品信息） Mapper 接口
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

}
