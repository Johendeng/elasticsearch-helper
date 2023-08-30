package org.pippi.elasticsearch.helper.core.reader.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.pippi.elasticsearch.helper.core.reader.ResponseBeanMapper;
import org.pippi.elasticsearch.helper.core.reader.ResponseReader;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ListClazzRespReader
 *
 * @author JohenTeng
 * @date 2022/5/11
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class CollectionRespReader implements ResponseReader<Collection<?>> {

    private static final CollectionRespReader LIST_RESP_READER = new CollectionRespReader();

    public static CollectionRespReader reader() {
        return LIST_RESP_READER;
    }

    private CollectionRespReader() {
    }

    @Override
    public boolean condition(Type returnType) {
        return returnType instanceof ParameterizedType
                && Collection.class.isAssignableFrom((Class<?>) ((ParameterizedType) returnType).getRawType());
    }

    @Override
    public Collection<?> read(Type returnType, SearchResponse resp) {
        Class<?> paramClazz = getFirstParameterizedType(returnType);
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        Collection records = ReflectionUtils.newCollection((Class<? extends Collection>) ((ParameterizedType)returnType).getRawType());
        int needWarm = 0;
        for (SearchHit hit : hitArr) {
            Object record = ResponseBeanMapper.map(paramClazz, hit.getSourceAsMap());
            records.add(record);
            needWarm += loadBaseHitData(record, hit, paramClazz);
        }
        if (needWarm > 0) {
            log.warn("response check has HighLight data, but result-type's parameterized-type not be extend BaseHit.class");
        }
        return records;
    }

    public <R extends EsEntity> List<R> read(Class<R> clazz, SearchResponse resp) {
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        return Arrays.stream(hitArr)
                .map(hit -> {
                    R cell = ResponseBeanMapper.map(clazz, hit.getSourceAsMap());
                    loadBaseHitData(cell, hit, clazz);
                    return cell;
                })
                .collect(Collectors.toList());
    }
}
