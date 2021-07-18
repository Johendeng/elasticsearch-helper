package org.pippi.elasticsearch.helper.beans;


import org.pippi.elasticsearch.helper.beans.enums.Meta;
import org.pippi.elasticsearch.helper.beans.enums.QueryType;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.web
 * date:    2021/5/3
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class QueryDes {

    private String column;

    private Object value;

    private Object[] values;

    private String queryType;

    private String meta;

    private String script;


    public QueryDes() {
    }

    public QueryDes(String column,
                    String queryType,
                    String meta,
                    String script) {
        this.column = column;
        this.queryType = queryType;
        this.meta = meta;
        this.script = script;
    }

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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
