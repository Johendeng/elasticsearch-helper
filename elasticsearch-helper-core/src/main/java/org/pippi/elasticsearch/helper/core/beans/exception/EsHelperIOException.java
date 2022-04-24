package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * @author    JohenTeng
 * @date     2021/7/17
 **/
public class EsHelperIOException extends RuntimeException {

    public EsHelperIOException() {
    }

    public EsHelperIOException(String message) {
        super(message);
    }

    public EsHelperIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperIOException(Throwable cause) {
        super(cause);
    }

    public EsHelperIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
