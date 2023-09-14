package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * OrderQueryBean
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
public class SourceOrderQueryConf extends QueryConf {

    private String script;

    private SortOrder sortOrder;

    private ScriptSortBuilder.ScriptSortType sortType;

    @Override
    public void configQueryBuilder(QueryBuilder queryBuilder) {
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ScriptSortBuilder.ScriptSortType getSortType() {
        return sortType;
    }

    public void setSortType(ScriptSortBuilder.ScriptSortType sortType) {
        this.sortType = sortType;
    }
}
