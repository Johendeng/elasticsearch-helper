package com.poet.elasticsearch.helper.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:Meta
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:01
 * Author:JohenTeng
 * Description:
 */
public enum  Meta {

    KEYWORD(1),
    TEXT(2),
    INT(3),
    DATETIME(4)
    ;

    private int code;

    Meta(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
