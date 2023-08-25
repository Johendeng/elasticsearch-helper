package org.pippi.elasticsearch.test.repository.entity.params;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MultiMatch;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SourceOrder;

/**
 * SourceOrderQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/9
 */
@EsQueryBean(index = "account", traceScore = true, size = 30)
public class SourceOrderQueryParam {


    @MultiMatch(boostFields = {"address:10.0", "email:5.0"}, type = MultiMatchQueryBuilder.Type.MOST_FIELDS)
    private String queryText;

    /**
     * 利用脚本排序将 低分排到前面
     */
    @SourceOrder(
            script = "1/(_score + 100)",
            sortType = ScriptSortBuilder.ScriptSortType.NUMBER, sortOrder = SortOrder.DESC)
    private String sorted;

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getSorted() {
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }
}
