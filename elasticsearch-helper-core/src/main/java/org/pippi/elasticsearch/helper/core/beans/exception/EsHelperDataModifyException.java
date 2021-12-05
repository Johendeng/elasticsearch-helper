package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsHelperDataModifyException
 * Package Name:com.poet.elasticsearch.helper.exception
 * Date:2021/4/30 11:55
 * author:JohenTeng
 * Description:
 */
public class EsHelperDataModifyException extends RuntimeException {

    public EsHelperDataModifyException() {
    }

    public EsHelperDataModifyException(String message) {
        super(message);
    }

    public EsHelperDataModifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperDataModifyException(Throwable cause) {
        super(cause);
    }

    public EsHelperDataModifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
