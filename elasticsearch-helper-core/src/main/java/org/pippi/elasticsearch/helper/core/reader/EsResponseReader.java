package org.pippi.elasticsearch.helper.core.reader;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseHit;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.reader.impl.BaseRespReader;
import org.pippi.elasticsearch.helper.core.reader.impl.ListRespReader;
import org.pippi.elasticsearch.helper.core.reader.impl.OriginalRespReader;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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
