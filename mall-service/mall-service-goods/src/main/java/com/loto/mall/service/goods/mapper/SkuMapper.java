package com.loto.mall.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loto.mall.api.goods.model.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 商品表（每个产品信息） Mapper 接口
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {
    /**
     * 库存递减
     *
     * @param id
     * @param num
     * @return
     */
    @Update("update sku set num=num-#{num} where id=#{id} and num>=#{num}")
    int deleteCount(@Param("id") String id, @Param("num") Integer num);
}
