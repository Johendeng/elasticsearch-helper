package org.pippi.elasticsearch.helper.core.reader.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.pippi.elasticsearch.helper.core.reader.ResponseReader;

import java.lang.reflect.Type;

/**
 * OriginalRespReader
 *
 * @author JohenTeng
 * @date 2022/5/11
 */
@SuppressWarnings(value = {"rawtypes"})
public class OriginalRespReader implements ResponseReader {

    private static final OriginalRespReader ORG_RESP_READER = new OriginalRespReader();

    public static OriginalRespReader reader() {
        return ORG_RESP_READER;
    }

    private OriginalRespReader() {
    }

    @Override
    public boolean condition(Type returnType) {
        return returnType.equals(SearchResponse.class);
    }

    @Override
    public Object read(Type returnType, SearchResponse resp) {
        return resp;
    }
}
