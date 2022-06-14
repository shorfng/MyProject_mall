package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.CategoryBrand;
import com.loto.mall.service.goods.mapper.CategoryBrandMapper;
import com.loto.mall.service.goods.service.ICategoryBrandService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类品牌关系表 服务实现类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements ICategoryBrandService {

}
