package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * @author  JohenTeng
 * @date 2021/4/30
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
