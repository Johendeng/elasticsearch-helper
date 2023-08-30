package org.pippi.elasticsearch.helper.core.reader;

import com.google.common.base.CaseFormat;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.config.ExtendQueryFeatureHolder;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/8/29
 **/
public class ResponseBeanMapper {
    private final Logger log = LoggerFactory.getLogger("RESP-BEAN-MAPPER");

    public static  <R> R map(Class<R> paramClazz, Map<String, Object> hitMap) {
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
}
