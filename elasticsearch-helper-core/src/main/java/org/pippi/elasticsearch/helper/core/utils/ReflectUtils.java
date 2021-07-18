package org.pippi.elasticsearch.helper.core.utils;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.core.utils
 * date:    2021/7/18
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class ReflectUtils {

    public static boolean isBaseType (Class<?> type) {
        if (
            type.equals(String.class) || type.equals(Byte.class) || type.equals(Short.class) || type.equals(Integer.class)
            || type.equals(Long.class) || type.equals(Float.class) || type.equals(Double.class) || type.equals(Boolean.class)
            || type.equals(Character.class)
        ) {
            return true;
        }
        return false;
    }




}
