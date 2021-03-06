package com.loto.mall.druid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.druid.model.HotGoods;
import com.loto.mall.druid.utils.DruidPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-29 15:26<p>
 * PageName：HotGoodsMapper.java<p>
 * Function：
 */

@Mapper
public interface HotGoodsMapper extends BaseMapper<HotGoods> {
    /**
     * 查询前 N 条记录
     *
     * @param size
     * @return
     */
    @Select("select ip, uri, __time as accessTime from mslog limit #{size}")
    List<HotGoods> topNum(Integer size);

    /**
     * 分页查询
     *
     * @param size
     * @param offset
     * @return
     */
    @Select("select ip, uri, __time as accessTime from mslog limit #{size} offset #{offset}")
    List<HotGoods> pageList(@Param("size") Integer size, @Param("offset") Long offset);

    /**
     * 分页查询 + 排序
     *
     * @param druidPage
     * @return
     */
    @Select("select ip, uri, __time as accessTime from mslog order by ${sort} ${sortType} limit #{size} offset #{offset}")
    List<HotGoods> pageListSort(DruidPage druidPage);

    /**
     * 时间查询（查询过去几小时前N条记录）
     *
     * @param size
     * @param time
     * @return
     */
    @Select("select ip, uri, __time as accessTime from mslog where __time>= timestamp '${time}' limit #{size}")
    List<HotGoods> searchTime(@Param("size") Integer size, @Param("time") String time);

    /**
     * 时间查询（排除指定数据）
     *
     * @param size
     * @param time
     * @param urls
     * @return
     */
    @Select("select ip, uri, __time as accessTime from mslog where __time>= timestamp '${time}' and uri not in('${urls}') limit #{size}")
    List<HotGoods> searchExclude(@Param("size") Integer size, @Param("time") String time, @Param("urls") String urls);

    /**
     * 查询热门商品（最近1小时内，根据查询数量排序，如果已经是分析过的热门商品，需要把它排除）
     * @param size
     * @param time
     * @param urls
     * @param max
     * @return
     */
    @Select("select uri, count(*) as viewCount from mslog where __time>=timestamp '${time}' and uri not in ('${urls}') group by uri having viewCount>#{max} order by viewCount desc limit #{size}")
    List<Map<String, String>> searchHotGoods(@Param("size") Integer size, @Param("time") String time, @Param("urls") String urls, @Param("max") Integer max);
}
