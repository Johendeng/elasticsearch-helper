package org.pippi.elasticsearch.helper.core.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.pippi.elasticsearch.helper.core.beans.exception.SerializeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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
     * @param <T> targetClass
     * @return target JavaBean(T)
     */
    public static <T>T mapping(Annotation annotation, Class<T> clazz) {
        Field[] extBeanFields = clazz.getDeclaredFields();
        try {
            Constructor<T> constructor = clazz.getConstructor(ArrayUtils.EMPTY_CLASS_ARRAY);
            T extBean = constructor.newInstance(ArrayUtils.EMPTY_OBJECT_ARRAY);
            Map<String, Object> annMapping = AnnotationUtils.toMap(annotation);
            for (Field field : extBeanFields) {
                field.setAccessible(true);
                String key = field.getName();
                Object val = annMapping.get(key);
                field.set(extBean, val);
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
