package com.loto.mall.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.seckill.model.SecKillActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 14:41<p>
 * PageName：SecKillActivityMapper.java<p>
 * Function：
 */

@Mapper
public interface SecKillActivityMapper extends BaseMapper<SecKillActivity> {
    /**
     * 查询有效活动列表
     */
    @Select("select * from seckill_activity where end_time>now() order by start_time asc limit 5")
    List<SecKillActivity> validActivity();
}
