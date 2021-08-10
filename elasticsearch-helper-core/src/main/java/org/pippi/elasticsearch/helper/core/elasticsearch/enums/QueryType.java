package org.pippi.elasticsearch.helper.core.elasticsearch.enums;

/**
 * 描述
 *
 * @author kelong@fiture.com
 * @date 2021/4/4
 */
public enum QueryType {

    UN_DEFINE("un_match", -1),
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
