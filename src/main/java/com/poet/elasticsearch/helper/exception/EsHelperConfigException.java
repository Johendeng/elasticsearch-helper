package com.poet.elasticsearch.helper.exception;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsHelperConfigException
 * Package Name:com.poet.elasticsearch.helper.exception
 * Date:2021/4/30 11:56
 * Author:dengtianjia
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
}
