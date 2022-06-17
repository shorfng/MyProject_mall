package com.loto.mall.es.mapper;

import com.loto.mall.api.es.model.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:05<p>
 * PageName：SkuSearchMapper.java<p>
 * Function：
 */

public interface SkuEsMapper extends ElasticsearchRepository<SkuEs, String> {

}