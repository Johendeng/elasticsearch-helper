package org.pippi.elasticsearch.helper.beans.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:QueryType
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:02
 * Author:JohenTeng
 * Description:
 */
public enum QueryType {

    LIKE("like",100),
    TERM("term",101),
    FUZZY("fuzzy",102),

    ;

    private String query;
    private int code;

    QueryType(String query, int code) {
        this.query = query;
        this.code = code;
    }

    public String getQuery() {
        return query;
    }

    public int getCode() {
        return code;
    }
}
