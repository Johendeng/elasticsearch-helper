package org.pippi.elasticsearch.helper.core.reader.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.pippi.elasticsearch.helper.core.utils.EsBeanMapper;
import org.pippi.elasticsearch.helper.core.reader.ResponseReader;
import org.pippi.elasticsearch.helper.model.resp.BaseResp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseRespReader
 *
 * @author JohenTeng
 * @date 2022/5/11
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class BaseRespReader implements ResponseReader<BaseResp<?>> {

    private static final BaseRespReader BASE_RESP_READER = new BaseRespReader();

    public static BaseRespReader reader() {
        return BASE_RESP_READER;
    }

    private BaseRespReader() {
    }

    @Override
    public boolean condition(Type returnType) {
        return returnType instanceof ParameterizedType
                && ((ParameterizedType) returnType).getRawType().equals(BaseResp.class);
    }

    @Override
    public  BaseResp<?> read(Type returnType, SearchResponse resp) {
        Class<?> paramClazz = getFirstParameterizedType(returnType);
        BaseResp res = new BaseResp();
        SearchHits hits = resp.getHits();
        res.setMaxScore(hits.getMaxScore());
        res.setTotalHit(hits.getTotalHits().value);
        SearchHit[] hitArr = hits.getHits();
        List records = new ArrayList<>(hitArr.length);
        int needWarn = 0;
        for (SearchHit hit : hitArr) {
            Object record = EsBeanMapper.toBean(paramClazz, hit.getSourceAsMap());
            records.add(record);
            needWarn += loadBaseHitData(record, hit, paramClazz);
        }
        res.setRecords(records);
        if (needWarn > 0) {
            log.warn("response check has HighLight data, but result-type's parameterized-type not be extend BaseHit.class");
        }
        return res;
    }
}
