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

    @Override
    public Object read(Type returnType, SearchResponse resp) {
        return resp;
    }
}
