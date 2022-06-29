package com.loto.mall.druid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.druid.model.HotGoods;
import com.loto.mall.druid.mapper.HotGoodsMapper;
import com.loto.mall.druid.service.HotGoodsService;
import com.loto.mall.druid.utils.DruidPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 15:34<p>
 * PageName：HotGoodsServiceImpl.java<p>
 * Function：
 */

@Service
public class HotGoodsServiceImpl extends ServiceImpl<HotGoodsMapper, HotGoods> implements HotGoodsService {
    @Autowired
    private HotGoodsMapper hotGoodsMapper;

    /**
     * 查询前 N 条记录
     *
     * @param size
     * @return
     */
    @Override
    public List<HotGoods> topNum(Integer size) {
        return hotGoodsMapper.topNum(size);
    }

    /**
     * 分页查询
     *
     * @param size
     * @param page
     * @return
     */
    @Override
    public DruidPage<List<HotGoods>> pageList(Integer size, Integer page) {
        // 计算偏移量
        DruidPage<List<HotGoods>> druidPage = new DruidPage<>(page, size);

        // 查询总数
        Integer total = hotGoodsMapper.selectCount(null);

        // 查询集合
        List<HotGoods> hotGoods = hotGoodsMapper.pageList(size, druidPage.getOffset());
        return druidPage.pages(hotGoods, total);
    }

    /**
     * 分页查询 + 排序
     *
     * @param size
     * @param page
     * @param sort
     * @param sortType
     * @return
     */
    @Override
    public DruidPage<List<HotGoods>> pageListSort(Integer size, Integer page, String sort, String sortType) {
        // 计算偏移量
        DruidPage<List<HotGoods>> druidPage = new DruidPage<List<HotGoods>>(page, size, sort, sortType);

        // 查询总数
        Integer total = hotGoodsMapper.selectCount(null);

        // 查询集合
        List<HotGoods> hotGoods = hotGoodsMapper.pageListSort(druidPage);
        return druidPage.pages(hotGoods, total);
    }
}
