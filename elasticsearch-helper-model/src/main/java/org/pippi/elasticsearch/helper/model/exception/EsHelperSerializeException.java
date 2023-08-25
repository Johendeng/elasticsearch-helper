package org.pippi.elasticsearch.helper.model.exception;

/**
 * @author JohenTeng
 * @date 2021/7/17
 */
public class EsHelperSerializeException extends RuntimeException {

    public EsHelperSerializeException() {
    }

    public EsHelperSerializeException(String message) {
        super(message);
    }

    public EsHelperSerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsHelperSerializeException(Throwable cause) {
        super(cause);
    }

    public EsHelperSerializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
