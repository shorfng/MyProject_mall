package com.loto.mall.search.util;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.bytes.ByteBufferReference;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.util.Map;

public class HighlightResultMapper extends DefaultResultMapper {
    /***
     * 映射转换，将非高亮数据替换成高亮数据
     * @param response
     * @param clazz
     * @param pageable
     * @param <T>
     * @return
     */
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        // 1、获取所有非高亮数据
        SearchHits hits = response.getHits();

        // 2、循环非高亮数据集合
        for (SearchHit hit : hits) {
            // 非高亮数据
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            // 3、获取高亮数据
            for (Map.Entry<String, HighlightField> entry : hit.getHighlightFields().entrySet()) {
                // 4、将非高亮数据替换成高亮数据
                String key = entry.getKey();

                // 如果当前非高亮对象 sourceAsMap 中有该高亮数据对应的非高亮对象，则进行替换
                if (sourceAsMap.containsKey(key)) {
                    // 高亮碎片
                    String highLightResult = transTxtToArrayToString(entry.getValue().getFragments());
                    if (!StringUtils.isEmpty(highLightResult)) {
                        // 替换高亮
                        sourceAsMap.put(key, highLightResult);
                    }
                }
            }

            // 更新hit的数据
            hit.sourceRef(new ByteBufferReference(ByteBuffer.wrap(JSONObject.toJSONString(sourceAsMap).getBytes())));
        }
        return super.mapResults(response, clazz, pageable);
    }

    /***
     * Text 转成字符串
     * @param fragments
     * @return
     */
    public String transTxtToArrayToString(Text[] fragments) {
        if (fragments != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Text fragment : fragments) {
                stringBuilder.append(fragment.toString());
            }
            return stringBuilder.toString();
        }

        return null;
    }
}
