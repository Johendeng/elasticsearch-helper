package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * OrderQueryBean
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
public class SourceOrderQueryBean extends QueryBean{

    private String sort;

    private String script;

    private String scriptType;

    @Override
    public void configQueryBuilder(QueryBuilder queryBuilder) {
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }
}
