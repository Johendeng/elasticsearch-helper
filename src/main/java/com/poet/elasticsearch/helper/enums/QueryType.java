package com.poet.elasticsearch.helper.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:QueryType
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:02
 * Author:dengtianjia
 * Description:
 */
public enum QueryType {

    LIKE(100),
    TERM(101),
    FUZZY(102),

    ;

    private int code;

    QueryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
