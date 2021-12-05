package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * project       Name:elasticsearch-helper
 * file        Name:EsHelperConfigException
 * package        Name:com.poet.elasticsearch.helper.exception
 * date 2021/4/30 11:56
 * author  JohenTeng
 * @description:
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
