package org.pippi.elasticsearch.helper.core.utils;

import org.pippi.elasticsearch.helper.core.beans.exception.SerializeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.core.utils
 * date:    2021/7/18
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
 **/
public class ReflectionUtils {

    /**
     *  judge the given type is Java-Base type or String, but not void.class
     * @param type
     * @return
     */
    public static boolean isBaseType (Class<?> type) {
        return ( type.isPrimitive() && !Objects.equals(type, void.class))
            || type.equals(String.class)  || type.equals(Boolean.class)
            || type.equals(Integer.class) || type.equals(Long.class) || type.equals(Short.class)
            || type.equals(Float.class)   || type.equals(Double.class)
            || type.equals(Byte.class)    || type.equals(Character.class);
    }

    /**
     *  initialize target-class
     * @param clazz target-class， must have no-args public constructor
     * @param <T> target-class-type
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> clazzConstructor = clazz.getConstructor();
            T targetBean = clazzConstructor.newInstance();
            return targetBean;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("target Class don't have No-args Constructor, cause: ", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("invoke targetConstructor Error, can't invoke , cause: ", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("invoke targetConstructor Error, cause: ", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("constructor isn't public, can't access, cause: ", e);
        }
    }

    /**
     * invoke target method
     */
    public static Object methodInvoke(Object instance, Method method, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Reflect-Invoke-TargetMethod IllegalAccessException Error,cause:", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Reflect-Invoke-TargetMethod InvocationTargetException Error,cause:", e);
        }
    }

    /**
     * use reflect to set a value for a given field
     */
    public static void setFieldValue (Object instance, Field field, Object val, boolean nullable) {
        try {
            field.setAccessible(true);
            if (Objects.nonNull(val) || nullable) {
                field.set(instance, val);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Reflect-setValue IllegalAccessException Error,cause:", e);
        }
    }


    public static Object getFieldValue (Field field , Object target) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Reflect-getValue IllegalAccessException Error, cause:", e);
        }
    }


}
