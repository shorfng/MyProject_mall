package com.loto.mall.search.mapper;

import com.loto.mall.api.search.model.SecKillGoodsEs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-27 16:12<p>
 * PageName：SecKillGoodsSearchMapper.java<p>
 * Function：
 */

@Mapper
public interface SecKillGoodsSearchMapper extends ElasticsearchRepository<SecKillGoodsEs, String> {

}
