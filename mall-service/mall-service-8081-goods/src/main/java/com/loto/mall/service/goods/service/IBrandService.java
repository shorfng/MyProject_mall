package com.loto.mall.service.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.goods.model.Brand;

import java.util.List;

public interface IBrandService extends IService<Brand> {
    /**
     * 条件查询
     */
    List<Brand> queryList(Brand brand);

    /**
     * 分页查询
     */
    Page<Brand> queryPageList(Brand brand, Long currentPage, Long size);

    /**
     * 查询品牌信息（根据分类ID）
     * @param parentId
     * @return
     */
    List<Brand> queryByCategoryId(Integer parentId);
}
