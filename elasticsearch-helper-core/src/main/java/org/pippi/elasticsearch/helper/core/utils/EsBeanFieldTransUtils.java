package org.pippi.elasticsearch.helper.core.utils;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.enums.UpdateStragegy;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/8/29
 **/
public class EsBeanFieldTransUtils {
    private final Logger log = LoggerFactory.getLogger("RESP-BEAN-MAPPER");

    public static  <R> R toBean(Class<R> paramClazz, Map<String, Object> hitMap) {
        Field[] fields = paramClazz.getDeclaredFields();
        R bean = ReflectionUtils.newInstance(paramClazz);
        for (Field field : fields) {
            String fieldName = field.getName();
            Object val = null;
            val = hitMap.get(fieldName);
            if (val == null && EsHelperConfiguration.mapUnderscoreToCamelCase) {
                  val = hitMap.get(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName));
            }
            if (val != null && !ReflectionUtils.isBaseType(field.getType())) {
                val = SerializerUtils.jsonToBean(SerializerUtils.parseObjToJson(val), field.getType());
            }
            ReflectionUtils.setFieldValue(bean, field, val, true);
        }
        return bean;
    }

    public static <E extends EsEntity> Map<String, Object> toMap(E bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        Map<String, Object> res = new HashMap<>();
        for (Field field : fields) {
            String key = field.getName();
            EsField fieldAnn = null;
            if (field.isAnnotationPresent(EsField.class) && StringUtils.isNotBlank((fieldAnn = field.getAnnotation(EsField.class)).name())) {
                key = fieldAnn.name();
            } else if (EsHelperConfiguration.mapUnderscoreToCamelCase) {
                key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);
            }
            Object val = ReflectionUtils.getFieldValueQuietly(field, bean);
            if (val != null && !ReflectionUtils.isBaseType(val.getClass())) {
                val = SerializerUtils.parseObjToJson(val);
            }
            res.put(key, val);
        }
        return res;
    }

    public static <E extends EsEntity> Map<String, Object> toUpdateMap(E bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        Map<String, Object> res = new HashMap<>();
        for (Field field : fields) {
            String key = field.getName();
            EsField fieldAnn = null;
            if (field.isAnnotationPresent(EsField.class) && StringUtils.isNotBlank((fieldAnn = field.getAnnotation(EsField.class)).name())) {
                key = fieldAnn.name();
            } else if (EsHelperConfiguration.mapUnderscoreToCamelCase) {
                key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);
            }
            Object val = ReflectionUtils.getFieldValueQuietly(field, bean);
            if (val != null && !ReflectionUtils.isBaseType(val.getClass())) {
                val = SerializerUtils.parseObjToJson(val);
            }
            if (UpdateStragegy.isGolbalNotNull() && val != null) {
                res.put(key, val);
            } else if(UpdateStragegy.isGolbalNotBlank()
                    && val != null
                    && ReflectionUtils.isBaseType(val.getClass())
                    && StringUtils.isNotBlank(val.toString())) {
                res.put(key, val);
            } else if (UpdateStragegy.isGolbalAny()) {
                res.put(key, val);
            }
        }
        return res;
    }

    public static String camelify(String fileName) {
        if (!EsHelperConfiguration.mapUnderscoreToCamelCase) {
            return fileName;
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileName);
    }
}
