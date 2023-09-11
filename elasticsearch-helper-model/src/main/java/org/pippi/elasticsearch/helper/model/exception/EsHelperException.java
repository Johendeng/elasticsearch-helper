package org.pippi.elasticsearch.helper.model.exception;

/**
 * @author    JohenTeng
 * @date     2021/7/17
 **/
public class EsHelperException extends RuntimeException {

    public EsHelperException() {
    }

    public EsHelperException(String message) {
        super(message);
    }

    public EsHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperException(Throwable cause) {
        super(cause);
    }

    public EsHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
