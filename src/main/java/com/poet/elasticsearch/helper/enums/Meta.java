package com.poet.elasticsearch.helper.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:Meta
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:01
 * Author:dengtianjia
 * Description:
 */
public enum  Meta {

    KEYWORD(1),
    TEXT(2),
    NUMERIC(3),
    DATE(4),
    OBJECT(10),
    ARRAY(11),
    ;

    private int code;



    Meta(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
