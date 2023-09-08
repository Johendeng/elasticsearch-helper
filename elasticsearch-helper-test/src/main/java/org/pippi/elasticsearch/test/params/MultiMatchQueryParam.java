package org.pippi.elasticsearch.test.params;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MultiMatch;

/**
 * MultiMatchQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
public class MultiMatchQueryParam {

    @MultiMatch(boostFields = {"address:10.0", "email:5.0", "city:20.0"},
            type = MultiMatchQueryBuilder.Type.MOST_FIELDS)
    private String queryText;

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
}
