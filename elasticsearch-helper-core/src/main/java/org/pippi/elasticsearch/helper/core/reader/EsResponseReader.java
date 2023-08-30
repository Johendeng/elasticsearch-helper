package org.pippi.elasticsearch.helper.core.reader;

import org.elasticsearch.action.search.SearchResponse;
import org.pippi.elasticsearch.helper.core.reader.impl.BaseRespReader;
import org.pippi.elasticsearch.helper.core.reader.impl.CollectionRespReader;
import org.pippi.elasticsearch.helper.core.reader.impl.OriginalRespReader;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author    JohenTeng
 * @date     2021/5/8
 *
 **/
public class EsResponseReader {


    private static final List<ResponseReader<?>> READER_LIST = new ArrayList<>();

    static {
        BaseRespReader.reader().register();
        CollectionRespReader.reader().register();
        OriginalRespReader.reader().register();
    }

    public static Object readResp(Method method, SearchResponse originalResp) {
        Type returnType = method.getGenericReturnType();
        for (ResponseReader<?> reader : READER_LIST) {
            if (reader.condition(returnType)) {
                return reader.read(returnType, originalResp);
            }
        }
        throw new EsHelperQueryException(
                "QueryMethod's return-type un-support, support type list: SearchResponse.class, Class<? extend Collection>, BaseResp.class");
    }

    static void readerRegister(ResponseReader reader) {
        READER_LIST.add(reader);
    }
}
