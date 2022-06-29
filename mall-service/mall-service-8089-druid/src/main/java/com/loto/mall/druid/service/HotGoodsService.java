package com.loto.mall.druid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loto.mall.api.druid.model.HotGoods;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 15:27<p>
 * PageName：HotGoodsService.java<p>
 * Function：
 */

public interface HotGoodsService extends IService<HotGoods> {
    /**
     * 查询前 N 条记录
     * @param size
     * @return
     */
    List<HotGoods> topNum(Integer size);
}
