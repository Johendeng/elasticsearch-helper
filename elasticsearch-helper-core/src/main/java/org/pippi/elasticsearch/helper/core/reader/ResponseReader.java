package org.pippi.elasticsearch.helper.core.reader;

import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ResponseReader
 *
 * @author JohenTeng
 * @date 2022/5/11
 */
public interface ResponseReader<R> {

    Logger log = LoggerFactory.getLogger(ResponseReader.class);

    Class getClazzKey();

    /**
     * 执行结果读取
     */
    R read(Type returnType, SearchResponse resp);


    default int loadBaseHitData(Object record, SearchHit hit, Class<?> paramType) {
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        if (BaseHit.class.isAssignableFrom(paramType) && (Objects.nonNull(highlightFields) && highlightFields.size() > 0)) {
            ((BaseHit)record).setDocId(hit.getId());
            ((BaseHit)record).setHitScore(hit.getScore());
            if (MapUtils.isNotEmpty(highlightFields)) {
                Map<String, List<String>> highLightMap = highlightFields.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, highlightField ->
                                Arrays.stream(highlightField.getValue().fragments()).map(Text::toString).collect(Collectors.toList())));
                ((BaseHit)record).setHighLightMap(highLightMap);
            }
            return 0;
        } else if (Objects.nonNull(highlightFields) && highlightFields.size() > 0) {
            return 1;
        }
        return 0;
    }
}
