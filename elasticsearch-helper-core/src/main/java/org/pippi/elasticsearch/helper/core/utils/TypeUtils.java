package org.pippi.elasticsearch.helper.core.utils;

import java.util.Objects;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.core.utils
 * date:    2021/7/18
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
 **/
public class TypeUtils {

    public static boolean isBaseType (Class<?> type) {
        return ( type.isPrimitive() && !Objects.equals(type, void.class))
            || type.equals(String.class) || type.equals(Boolean.class)
            || type.equals(Integer.class) || type.equals(Long.class) || type.equals(Short.class)
            || type.equals(Float.class) || type.equals(Double.class)
            || type.equals(Byte.class) || type.equals(Character.class);
    }

}
