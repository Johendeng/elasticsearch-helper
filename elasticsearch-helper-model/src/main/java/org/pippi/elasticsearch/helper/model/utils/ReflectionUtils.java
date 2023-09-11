package org.pippi.elasticsearch.helper.model.utils;

import com.google.common.collect.Lists;
import org.pippi.elasticsearch.helper.model.utils.support.SetAccessibleAction;

import java.lang.reflect.*;
import java.security.AccessController;
import java.util.*;

/**
 * @author    JohenTeng
 * @date     2021/7/18
 **/
public class ReflectionUtils {

    /**
     *  judge the given type is Java-Base type or String, but not void.class
     * @param type
     * return
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
     * return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> clazzConstructor = clazz.getConstructor();
            return clazzConstructor.newInstance();
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

    /**
     * use reflect to get value from given obj
     */
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

    public static Object getFieldValueQuietly(Field field , Object target) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            return null;
        }
    }


    public static Collection transArrayOrCollection(Object targetObj) {
        Class<?> returnType = targetObj.getClass();
        //判断返回类型是否是集合类型
        boolean isCollection = Collection.class.isAssignableFrom(returnType);
        if (isCollection) {
            return (Collection) targetObj;
        }
        //判断返回类型是否是数组类型
        boolean isArray = returnType.isArray();
        if (isArray) {
            int length = Array.getLength(targetObj);
            List<Object> arr = Lists.newArrayList();
            for (int i = 0; i < length; i ++) {
                arr.add(Array.get(targetObj, i));
            }
            return arr;
        }
        throw new RuntimeException("Reflect trans collection error");
    }

    /**
     * 获取目标参数化类型
     */
    public static Type[] getParameterizedTypes(Type targetClazz) {
        if (!(targetClazz instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType paramReturnType = (ParameterizedType)targetClazz;
        return paramReturnType.getActualTypeArguments();
    }

    public static  <C extends Collection<?>> C newCollection(Class<C> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            if (List.class.isAssignableFrom(clazz)) {
                return (C) new ArrayList<>();
            }
            if (Set.class.isAssignableFrom(clazz)) {
                return (C) new HashSet<>();
            }
            if (Queue.class.isAssignableFrom(clazz)) {
                return (C) new LinkedList<>();
            }
        }
        return newInstance(clazz);
    }

    public static List<Field> getFields(Class<?> clazz, boolean visitParent) {
        if (visitParent) {
            return getFields(clazz, Lists.newArrayList());
        }
        Field[] fieldArr = clazz.getDeclaredFields();
        return Lists.newArrayList(fieldArr);
    }

    private static List<Field> getFields(Class<?> clazz, List<Field> callBackList) {
        Field[] fieldArr = clazz.getDeclaredFields();
        callBackList.addAll(Arrays.asList(fieldArr));
        if (!(clazz = clazz.getSuperclass()).equals(Object.class)) {
            getFields(clazz, callBackList);
        }
        return callBackList;
    }

    /**
     * 设置可访问对象的可访问权限为 true
     *
     * @param object 可访问的对象
     * @param <T>    类型
     * @return 返回设置后的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T object) {
        return AccessController.doPrivileged(new SetAccessibleAction<>(object));
    }
}
