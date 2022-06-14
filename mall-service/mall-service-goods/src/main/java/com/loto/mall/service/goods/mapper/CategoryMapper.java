package com.loto.mall.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.goods.model.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品类目 Mapper 接口
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
