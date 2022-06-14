package com.loto.mall.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.goods.model.SkuAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 属性表 Mapper 接口
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Mapper
public interface SkuAttributeMapper extends BaseMapper<SkuAttribute> {
    /**
     * 查询属性集合（根据分类ID）
     * @param id
     * @return
     */
    @Select("select * from sku_attribute where id in(select attr_id from category_attr where category_id=#{id})")
    List<SkuAttribute> queryByCategoryId(Integer id);
}
