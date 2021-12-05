package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * Project Name:elasticsearch-helper
 * File Name:SerializeException
 * Package Name:com.poet.elasticsearch.helper.beans.exception
 * Date:2021/7/17 15:23
 * author: JohenTeng
 * Description:
 */
public class SerializeException extends RuntimeException {

    public SerializeException() {
    }

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializeException(Throwable cause) {
        super(cause);
    }

    public SerializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
