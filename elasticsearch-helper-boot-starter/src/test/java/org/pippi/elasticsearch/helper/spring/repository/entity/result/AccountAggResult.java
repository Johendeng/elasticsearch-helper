package org.pippi.elasticsearch.helper.spring.repository.entity.result;

import org.elasticsearch.action.search.SearchResponse;

/**
 * AccountAggResult
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
public class AccountAggResult {

    private SearchResponse resp;

    public SearchResponse getResp() {
        return resp;
    }

    public void setResp(SearchResponse resp) {
        this.resp = resp;
    }
}
