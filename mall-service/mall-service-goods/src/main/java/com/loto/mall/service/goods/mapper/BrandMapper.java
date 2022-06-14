package com.loto.mall.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.goods.model.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 查询品牌信息（根据分类ID）
     * @param parentId
     * @return
     */
    @Select("select brand_id from category_brand where category_id=#{id}")
    List<Integer> queryBrandIds(Integer parentId);
}
