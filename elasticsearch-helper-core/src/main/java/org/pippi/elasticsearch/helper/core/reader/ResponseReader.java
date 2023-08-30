package org.pippi.elasticsearch.helper.core.reader;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ResponseReader
 *
 * @author JohenTeng
 * @date 2022/5/11
 */
public interface ResponseReader<R> {

    Logger log = LoggerFactory.getLogger(ResponseReader.class);

    boolean condition(Type returnType);

    /**
     * 执行结果读取
     */
    R read(Type returnType, SearchResponse resp);


    default int loadBaseHitData(Object record, SearchHit hit, Class<?> paramType) {
        if (!EsEntity.class.isAssignableFrom(paramType)) {
            return 1;
        }
        ((EsEntity)record).setDocId(hit.getId());
        ((EsEntity)record).setHitScore(hit.getScore());
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        if (MapUtils.isNotEmpty(highlightFields)) {
            Map<String, List<String>> highLightMap = highlightFields.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, highlightField ->
                            Arrays.stream(highlightField.getValue().fragments()).map(Text::toString).collect(Collectors.toList())));
            ((EsEntity)record).setHighLightMap(highLightMap);
        }
        return 0;
    }

    default Class<?> getFirstParameterizedType(Type returnType) {
        Type[] parameterizedTypes = ReflectionUtils.getParameterizedTypes(returnType);
        Class<?> paramClazz = Map.class;
        if (ArrayUtils.isNotEmpty(parameterizedTypes)) {
            paramClazz = (Class<?>) parameterizedTypes[0];
        }
        return paramClazz;
    }

    default void register() {
        EsResponseReader.readerRegister(this);
    }
}
