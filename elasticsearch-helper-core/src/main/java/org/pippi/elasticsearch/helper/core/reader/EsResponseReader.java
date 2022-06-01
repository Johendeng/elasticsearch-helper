package org.pippi.elasticsearch.helper.core.reader;

import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchResponse;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.utils.ReflectionUtils;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author    JohenTeng
 * @date     2021/5/8
 *
 **/
public class EsResponseReader {

    private static final Map<Type, ResponseReader<?>> READER_MAP = Maps.newHashMap();

    static {
        Reflections reflections = new Reflections();
        Set<Class<? extends ResponseReader>> subClazzSet = reflections.getSubTypesOf(ResponseReader.class);
        for (Class<? extends ResponseReader> clazz : subClazzSet) {
            ResponseReader responseReader = ReflectionUtils.newInstance(clazz);
            READER_MAP.put(responseReader.getClazzKey() , responseReader);
        }
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
