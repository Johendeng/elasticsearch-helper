package org.pippi.elasticsearch.test.repository.entity.params;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MultiMatch;

/**
 * MultiMatchQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
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
