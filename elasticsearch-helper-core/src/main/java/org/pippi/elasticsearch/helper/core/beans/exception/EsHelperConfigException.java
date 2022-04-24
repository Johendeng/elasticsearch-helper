package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * @author  JohenTeng
 * @date 2021/4/30 11:56
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
