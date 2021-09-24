package org.pippi.elasticsearch.helper.core.beans.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:QueryType
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:02
 * Author:JohenTeng
 * Description:
 */
public enum QueryType {

    UN_DEFINE("un_match", -1),

    TERM("term",101),
    FUZZY("fuzzy",102),
    MATCH("match",103),
    MULTI_MATCH("multi_match", 104),
    WILD_CARD("wildcard", 105),
    RANGE("range", 106),
    NESTED("nested", 201),
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
