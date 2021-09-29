package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsHelperConfigException
 * Package Name:com.poet.elasticsearch.helper.exception
 * Date:2021/4/30 11:56
 * Author:JohenTeng
 * Description:
 */
public class EsHelperConfigException extends RuntimeException {

    public EsHelperConfigException() {
    }

    public EsHelperConfigException(String message) {
        super(message);
    }

    public EsHelperConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperConfigException(Throwable cause) {
        super(cause);
    }

    public EsHelperConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
