package org.pippi.elasticsearch.helper.core.beans.enums;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.*;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Fuzzy;

import java.lang.annotation.Annotation;

/**
 * @Project Name:elasticsearch-helper
 * @File Name:QueryType
 * @Package Name:com.poet.elasticsearch.helper.enums
 * @Date:2021/4/30 12:02
 * @Author:JohenTeng
 * @Description:
 */
public enum QueryType {

    /**
     *
     */
    UN_DEFINE("un_match", -1, null),

    TERM("term",101, Term.class),
    TERMS("term",101, Terms.class),
    FUZZY("fuzzy",102, Fuzzy.class),
    MATCH("match",103, Match.class),
    MULTI_MATCH("multi_match", 104, MultiMatch.class),
    WILD_CARD("wildcard", 105, null),
    RANGE("range", 106, null),
    NESTED("nested", 201, Nested.class),
    ;

    private String query;
    private int code;
    private Class<? extends Annotation> annotation;

    QueryType(String query, int code, Class<? extends Annotation> annotation) {
        this.query = query;
        this.code = code;
        this.annotation = annotation;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }
}
