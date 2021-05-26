package com.poet.elasticsearch.helper.web;

import com.poet.elasticsearch.helper.enums.Meta;
import com.poet.elasticsearch.helper.enums.QueryType;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.web
 * date:    2021/5/3
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class QueryDes {

    private String column;

    private Object value;

    private Object[] values;

    private QueryType queryType;

    private Meta meta;




    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
