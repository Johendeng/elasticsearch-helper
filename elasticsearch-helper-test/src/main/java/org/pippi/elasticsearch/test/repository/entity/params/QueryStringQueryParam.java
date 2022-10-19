package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.QueryString;

/**
 * @author: JohenTeng
 * @date: 2022/5/8
 **/
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class QueryStringQueryParam {

    @QueryString(fieldAndBoost = "address:5.0")
    private String queryText;

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
}
