package org.pippi.elasticsearch.helper.model.utils;

import org.pippi.elasticsearch.helper.model.exception.EsHelperException;

/**
 * @author JohenDeng
 * @date 2023/9/11
 **/
public class ExceptionUtils {


    private ExceptionUtils() {
    }

    /**
     * 返回一个新的异常，统一构建，方便统一处理
     *
     * @param msg 消息
     * @param t   异常信息
     * @return 返回异常
     */
    public static EsHelperException mpe(String msg, Throwable t, Object... params) {
        return new EsHelperException(String.format(msg, params), t);
    }

    /**
     * 重载的方法
     *
     * @param msg 消息
     * @return 返回异常
     */
    public static EsHelperException mpe(String msg, Object... params) {
        return new EsHelperException(String.format(msg, params));
    }

    /**
     * 重载的方法
     *
     * @param t 异常
     * @return 返回异常
     */
    public static EsHelperException mpe(Throwable t) {
        return new EsHelperException(t);
    }

}
