package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.Category;
import com.loto.mall.service.goods.mapper.CategoryMapper;
import com.loto.mall.service.goods.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有子类（根据分类的 parentId）
     * @param parentId
     * @return
     */
    @Override
    public List<Category> findByParentId(Integer parentId) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("parent_id", parentId);

        return categoryMapper.selectList(categoryQueryWrapper);
    }
}
