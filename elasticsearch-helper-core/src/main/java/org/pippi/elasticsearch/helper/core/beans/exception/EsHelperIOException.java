package org.pippi.elasticsearch.helper.core.beans.exception;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.beans.exception
 * date:    2021/7/17
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
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
