package com.poet.elasticsearch.helper.exception;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsHelperDDLException
 * Package Name:com.poet.elasticsearch.helper.exception
 * Date:2021/4/30 11:55
 * Author:JohenTeng
 * Description:
 */
public class EsHelperDDLException extends RuntimeException {

    public EsHelperDDLException() {
    }

    public EsHelperDDLException(String message) {
        super(message);
    }

    public EsHelperDDLException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperDDLException(Throwable cause) {
        super(cause);
    }

    public EsHelperDDLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
