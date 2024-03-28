package org.pippi.elasticsearch.helper.model.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.exception.EsHelperSerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

/**
 * @author    JohenTeng
 * @date     2021/3/16
 **/
public class JacksonUtils {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);


    private static final ObjectMapper _NORMAL_MAPPER = initDefaultMapper();


    private static final ObjectMapper _UN_MATCH_NULL_MAPPER = initDefaultMapper()
                                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static ObjectMapper initDefaultMapper() {
        return new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            // A反序列化成B，A的部分属性B中没有 不报错
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .setPropertyNamingStrategy(new EsHelperPropertyNamingStrategy())
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
            return _UN_MATCH_NULL_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
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

    protected static class EsHelperPropertyNamingStrategy extends PropertyNamingStrategy {

        public EsHelperPropertyNamingStrategy() {
            super();
        }

        @Override
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            EsField fieldAnn = field.getAnnotation(EsField.class);
            if (fieldAnn != null) {
                String name = fieldAnn.name();
                if (StringUtils.isNotBlank(name)) {
                    return name;
                }
            }
            // 是否进行驼峰转换
            if (!EsHelperConfiguration.mapUnderscoreToCamelCase) {
                return defaultName;
            }
            return CommonUtils.toLowerCamel(defaultName);
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            Class<?> clazz = method.getDeclaringClass();
            if (ReflectionUtils.isBaseType(clazz)) return defaultName;
            try {
                Field field = clazz.getDeclaredField(defaultName);
                EsField fieldAnn = field.getAnnotation(EsField.class);
                if (fieldAnn != null) {
                    String name = fieldAnn.name();
                    if (StringUtils.isNotBlank(name)) {
                        return name;
                    }
                }
                // 是否进行驼峰转换
                if (!EsHelperConfiguration.mapUnderscoreToCamelCase) {
                    return defaultName;
                }
                return CommonUtils.toLowerUnderscore(defaultName);
            } catch (NoSuchFieldException e) {
                return defaultName;
            }
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            Class<?> clazz = method.getDeclaringClass();
            if (ReflectionUtils.isBaseType(clazz)) return defaultName;
            try {
                Field field = clazz.getDeclaredField(defaultName);
                EsField fieldAnn = field.getAnnotation(EsField.class);
                if (fieldAnn != null) {
                    String name = fieldAnn.name();
                    if (StringUtils.isNotBlank(name)) {
                        return name;
                    }
                }
                // 是否进行驼峰转换
                if (!EsHelperConfiguration.mapUnderscoreToCamelCase) {
                    return defaultName;
                }
                return CommonUtils.toLowerCamel(defaultName);
            } catch (NoSuchFieldException e) {
                return defaultName;
            }
        }

        @Override
        public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
            return super.nameForConstructorParameter(config, ctorParam, defaultName);
        }
    }

    @SuppressWarnings("all")
    public static class EsHelperTypeReference<T> extends TypeReference<T> {

//        private Type type;

        public EsHelperTypeReference() {
        }

//        public EsHelperTypeReference(Field field) {
//            if (field.getGenericType() instanceof ParameterizedType) {
//                this.type = field.getGenericType();
//            } else {
//                this.type = field.getType();
//            }
//        }
//
//        @Override
//        public Type getType() {
//            return this.type;
//        }
    }
}
