package org.pippi.elasticsearch.helper.core.reader;

import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchResponse;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.model.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.reader.impl.BaseRespReader;
import org.pippi.elasticsearch.helper.core.reader.impl.ListRespReader;
import org.pippi.elasticsearch.helper.core.reader.impl.OriginalRespReader;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author    JohenTeng
 * @date     2021/5/8
 *
 **/
public class EsResponseReader {

    private static final Map<Type, ResponseReader<?>> READER_MAP = Maps.newHashMap();

    static {
        READER_MAP.put(List.class, new ListRespReader());
        READER_MAP.put(BaseResp.class, new BaseRespReader());
        READER_MAP.put(SearchResponse.class, new OriginalRespReader());
    }

    public static Object readResp(Method method, SearchResponse originalResp) {
        ResponseReader<?> reader = READER_MAP.get(method.getReturnType());
        if (Objects.isNull(reader)) {
            throw new EsHelperQueryException("QueryMethod's return-type un-support, support type list:" +
                SerializerUtils.parseObjToJson(READER_MAP.keySet()));
        }
        return reader.read(method.getGenericReturnType(), originalResp);
    }
}
