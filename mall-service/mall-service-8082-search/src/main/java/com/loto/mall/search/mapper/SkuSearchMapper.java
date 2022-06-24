package com.loto.mall.search.mapper;

import com.loto.mall.api.search.model.SkuSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-17 20:05<p>
 * PageName：SkuSearchMapper.java<p>
 * Function：
 */

public interface SkuSearchMapper extends ElasticsearchRepository<SkuSearch, String> {

}
