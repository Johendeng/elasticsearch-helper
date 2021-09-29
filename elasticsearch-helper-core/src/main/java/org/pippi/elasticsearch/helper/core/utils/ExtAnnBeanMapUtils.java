package org.pippi.elasticsearch.helper.core.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.pippi.elasticsearch.helper.core.beans.exception.SerializeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

/**
 * ExtAnnBeanMapUtils
 *
 * @author JohenTeng
 * @date 2021/9/23
 */
public class ExtAnnBeanMapUtils {


    /**
     *  phrase annotation to JavaBean
     * @param annotation target Annotation
     * @param clazz mapping class
     * @return target JavaBean(T)
     */
    public static Object mapping(Annotation annotation, Class<?> clazz) {
        Field[] extBeanFields = clazz.getDeclaredFields();
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object extBean = constructor.newInstance();
            Map<String, Object> annMapping = AnnotationUtils.toMap(annotation);
            for (Field field : extBeanFields) {
                field.setAccessible(true);
                String key = field.getName();
                Object val = annMapping.get(key);
                if (Objects.nonNull(val)) {
                    field.set(extBean, val);
                }
            }
            return extBean;
        } catch (NoSuchMethodException e) {
            throw new SerializeException("annotation mapping Error NoSuchMethodException, cause",e);
        } catch (InvocationTargetException e) {
            throw new SerializeException("annotation mapping Error InvocationTargetException, cause",e);
        } catch (InstantiationException e) {
            throw new SerializeException("annotation mapping Error InstantiationException, cause",e);
        } catch (IllegalAccessException e) {
            throw new SerializeException("annotation mapping Error IllegalAccessException, cause",e);
        }
    }

}
