package org.pippi.elasticsearch.helper.core.utils;

/**
 * @author JohenDeng
 * @date 2023/8/29
 **/
public class EsAssert {

    public static void isTrue(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
