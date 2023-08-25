package org.pippi.elasticsearch.helper.core.reader.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.pippi.elasticsearch.helper.core.reader.ResponseReader;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ListClazzRespReader
 *
 * @author JohenTeng
 * @date 2022/5/11
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class ListRespReader implements ResponseReader<List<?>> {

    @Override
    public List<?> read(Type returnType, SearchResponse resp) {
        Type[] parameterizedTypes = ReflectionUtils.getParameterizedTypes(returnType);
        Class<?> paramClazz = Map.class;
        if (ArrayUtils.isNotEmpty(parameterizedTypes)) {
            paramClazz = (Class<?>) parameterizedTypes[0];
        }
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        List records = new ArrayList<>(hitArr.length);
        int needWarm = 0;
        for (SearchHit hit : hitArr) {
            String recordJson = hit.getSourceAsString();
            Object record = SerializerUtils.jsonToBean(recordJson, paramClazz);
            records.add(record);
            needWarm += loadBaseHitData(record, hit, paramClazz);
        }
        if (needWarm > 0) {
            log.warn("response check has HighLight data, but result-type's parameterized-type not be extend BaseHit.class");
        }
        return records;
    }
}
