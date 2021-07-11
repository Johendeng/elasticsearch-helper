package com.poet.elasticsearch.helper.beans.exception;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsHelperQueryException
 * Package Name:com.poet.elasticsearch.helper.exception
 * Date:2021/4/30 11:55
 * Author:JohenTeng
 * Description:
 */
public class EsHelperQueryException extends RuntimeException {

    public EsHelperQueryException() {
    }

    public EsHelperQueryException(String message) {
        super(message);
    }

    public EsHelperQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperQueryException(Throwable cause) {
        super(cause);
    }

    public EsHelperQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
