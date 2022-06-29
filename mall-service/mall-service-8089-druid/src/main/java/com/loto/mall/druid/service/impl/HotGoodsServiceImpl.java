package com.loto.mall.druid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.druid.model.HotGoods;
import com.loto.mall.druid.mapper.HotGoodsMapper;
import com.loto.mall.druid.service.HotGoodsService;
import com.loto.mall.druid.utils.DruidPage;
import com.loto.mall.druid.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 时间查询（查询过去几小时前N条记录）
     *
     * @param size
     * @param hour
     * @return
     */
    @Override
    public List<HotGoods> searchTime(Integer size, Integer hour) {
        return hotGoodsMapper.searchTime(size, TimeUtil.beforeTime(TimeUtil.unit_hour, hour));
    }

    /**
     * 时间查询（排除指定数据）
     *
     * @param size
     * @param hour
     * @param urls
     * @return
     */
    @Override
    public List<HotGoods> searchExclude(Integer size, Integer hour, String[] urls) {
        // 拼接 urls ---> 123.html','2342.html','324234.html
        String allUrls = StringUtils.join(urls, "','");
        return hotGoodsMapper.searchExclude(size, TimeUtil.beforeTime(TimeUtil.unit_hour, hour), allUrls);
    }

    /**
     * 查询热门商品（最近1小时内，根据查询数量排序，如果已经是分析过的热门商品，需要把它排除）
     *
     * @param size
     * @param hour
     * @param urls
     * @param max
     * @return
     */
    @Override
    public List<Map<String, String>> searchHotGoods(Integer size, Integer hour, String[] urls, Integer max) {
        // 拼接 urls ---> 123.html','2342.html','324234.html
        String allUrls = StringUtils.join(urls, "','");
        return hotGoodsMapper.searchHotGoods(size, TimeUtil.beforeTime(TimeUtil.unit_hour, hour), allUrls, max);
    }
}
