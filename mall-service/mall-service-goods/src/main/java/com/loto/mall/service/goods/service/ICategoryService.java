package com.loto.mall.service.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.goods.model.Category;

import java.util.List;

/**
 * <p>
 * 商品类目 服务类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 查询所有子类（根据分类的 parentId）
     * @param parentId
     * @return
     */
    List<Category> findByParentId(Integer parentId);
}
