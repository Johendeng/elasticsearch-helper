package org.pippi.elasticsearch.helper.core.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.pippi.elasticsearch.helper.model.exception.EsHelperSerializeException;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author    JohenTeng
 * @date     2021/3/16
 **/
public class SerializerUtils {

    private static final Logger log = LoggerFactory.getLogger(SerializerUtils.class);


    private static final ObjectMapper _NORMAL_MAPPER = initDefaultMapper();


    private static final ObjectMapper _UN_MATCH_NULL_MAPPER = initDefaultMapper()
                                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static ObjectMapper initDefaultMapper() {
        return new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }


    public static <T> T jsonToBean(String json, Class<T> clazz) {
        try {
            return _NORMAL_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new EsHelperSerializeException("Json-String trans to Java-Bean error, cause:", e);
        }
    }

    public static <T>T jsonToBeans(String json, TypeReference<T> reference) {
        try {
            return  _UN_MATCH_NULL_MAPPER.readValue(json, reference);
        } catch (JsonProcessingException e) {
            throw new EsHelperSerializeException("Json-String trans to Java-Bean error, cause:", e);
        }
    }

    public static String parseObjToJson(Object obj) {
        try {
            if (obj == null) {
                return "null";
            }
            if (ReflectionUtils.isBaseType(obj.getClass())) {
                return obj.toString();
            }
            return _NORMAL_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new EsHelperSerializeException("Object normal trans to Json-String error, cause:", e);
        }
    }

    public static String parseObjToJsonPretty(Object obj) {
        try {
            return _NORMAL_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new EsHelperSerializeException("Object normal trans to Json-String error, cause:", e);
        }
    }

    public static String parseObjToJsonSkipNull(Object obj) {
        try {
            return _UN_MATCH_NULL_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new EsHelperSerializeException("Object trans to json-string error, cause:", e);
        }
    }
}
