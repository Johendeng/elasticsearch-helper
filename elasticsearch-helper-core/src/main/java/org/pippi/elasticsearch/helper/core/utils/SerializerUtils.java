package org.pippi.elasticsearch.helper.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pippi.elasticsearch.helper.beans.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.utils
 * date:    2021/3/16
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class SerializerUtils {

    private static final Logger log = LoggerFactory.getLogger(SerializerUtils.class);


    private static final ObjectMapper _NORMAL_MAPPER = initDefaultMapper();


    private static final ObjectMapper _UN_MATCH_NULL_MAPPER = initDefaultMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static ObjectMapper initDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();

        return mapper;
    }


    public static <T> T jsonToBean(String json, Class<T> clazz) {
        try {
            return _NORMAL_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new SerializeException("Json-String trans to Java-Bean error, cause:", e);
        }
    }


    public static String parseObjToJson(Object obj) {
        try {
            return _NORMAL_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializeException("Object normal trans to Json-String error, cause:", e);
        }
    }

    public static String parseObjToJsonPretty(Object obj) {
        try {
            return _NORMAL_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializeException("Object normal trans to Json-String error, cause:", e);
        }
    }

    public static String parseObjToJsonSkipNull(Object obj) {
        try {
            return _UN_MATCH_NULL_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializeException("Object trans to json-string error, cause:", e);
        }
    }


}
