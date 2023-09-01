package org.pippi.elasticsearch.helper.core.utils;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
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
public class EsBeanMapper {
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
                // todo 对于复杂类型的数据结构不知道会不会有问题
                val = SerializerUtils.parseObjToJson(val);
            }
            res.put(key, val);
        }
        return res;
    }
}
