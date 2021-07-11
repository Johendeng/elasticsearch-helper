package com.poet.elasticsearch.helper.beans.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:QueryType
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:02
 * Author:JohenTeng
 * Description:
 */
public enum QueryType {

    TERM(1),


    ;

    private int code;

    QueryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
