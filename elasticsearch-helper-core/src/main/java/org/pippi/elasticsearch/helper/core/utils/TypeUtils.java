package org.pippi.elasticsearch.helper.core.utils;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.core.utils
 * date:    2021/7/18
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class TypeUtils {

    public static boolean isBaseType (Class<?> type) {
        return type.isPrimitive() || type.equals(String.class);
    }




}
