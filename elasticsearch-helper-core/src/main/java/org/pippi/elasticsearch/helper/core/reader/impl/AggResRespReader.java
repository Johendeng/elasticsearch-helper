package org.pippi.elasticsearch.helper.core.reader.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.pippi.elasticsearch.helper.core.reader.ResponseReader;
import org.pippi.elasticsearch.helper.model.resp.AggRes;
import org.pippi.elasticsearch.helper.model.utils.AggResponseVisitor;

import java.lang.reflect.Type;

/**
 * @author JohenDeng
 * @date 2023/9/20
 **/
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class AggResRespReader  implements ResponseReader<AggRes> {

    private static final AggResRespReader AGG_RES_RESP_READER = new AggResRespReader();

    public static AggResRespReader reader() {
        return AGG_RES_RESP_READER;
    }

    private AggResRespReader() {
    }

    @Override
    public boolean condition(Type returnType) {
        return returnType.equals(AggRes.class);
    }

    @Override
    public AggRes read(Type returnType, SearchResponse resp) {
        Aggregations aggregations = resp.getAggregations();
        if (aggregations == null) {
            return null;
        }
        return AggResponseVisitor.run(aggregations);
    }
}
