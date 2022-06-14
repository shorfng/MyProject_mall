package com.loto.mall.service.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.goods.model.Brand;
import com.loto.mall.service.goods.mapper.BrandMapper;
import com.loto.mall.service.goods.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 条件查询
     */
    @Override
    public List<Brand> queryList(Brand brand) {
        // 条件包装对象
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();

        // 根据name查询品牌
        queryWrapper.like("name", brand.getName());

        // 根据initial查询
        queryWrapper.eq("initial", brand.getInitial());

        return brandMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Brand> queryPageList(Brand brand, Long currentPage, Long size) {
        // 条件包装对象
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();

        // 根据name查询品牌
        queryWrapper.like("name", brand.getName());

        return brandMapper.selectPage(new Page<Brand>(currentPage, size), queryWrapper);
    }

    /**
     * 查询品牌信息（根据分类ID）
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Brand> queryByCategoryId(Integer parentId) {
        // 查询品牌信息（根据分类ID）
        List<Integer> brandIds = brandMapper.queryBrandIds(parentId);

        // 查询品牌集合（根据品牌ID）
        if (brandIds != null && brandIds.size() > 0) {
            return brandMapper.selectList(new QueryWrapper<Brand>().in("id", brandIds));
        }

        return null;
    }
}
