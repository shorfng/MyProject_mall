package com.loto.mall.druid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.druid.model.HotGoods;
import com.loto.mall.druid.utils.DruidPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

}
