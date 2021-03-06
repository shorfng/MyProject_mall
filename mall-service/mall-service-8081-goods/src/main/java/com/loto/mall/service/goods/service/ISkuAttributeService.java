package com.loto.mall.service.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.goods.model.SkuAttribute;

import java.util.List;

/**
 * <p>
 * 属性表 服务类
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
public interface ISkuAttributeService extends IService<SkuAttribute> {
    /**
     * 查询属性集合（根据分类ID）
     * @param id
     * @return
     */
    List<SkuAttribute> queryList(Integer id);
}
