package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.QueryString;

/**
 * @author: JohenTeng
 * @date: 2022/5/8
 **/
@EsQueryBean(index = "account", traceScore = true, size = 30)
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
