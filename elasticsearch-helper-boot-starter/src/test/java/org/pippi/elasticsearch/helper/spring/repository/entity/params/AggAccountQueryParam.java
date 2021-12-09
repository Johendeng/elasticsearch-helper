package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Term;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountAggResult;

/**
 * 实现自定义聚合，自定义结果返回
 *
 * @author dengtianjia@fiture.com
 * @date 2021/12/9
 */
@EsQueryIndex(index = "account", model = QueryModel.BOOL)
public class AggAccountQueryParam extends HookQuery<AggAccountQueryParam, AccountAggResult> {

    @Term
    private String city;

    @Override
    protected void configRequestHook(AbstractEsRequestHolder holder, AggAccountQueryParam aggAccountQueryParam) {
        SearchSourceBuilder source = holder.getSource();
        source.aggregation(
                AggregationBuilders.terms("_age").field("age").subAggregation(
                        AggregationBuilders.count("_age_count").field("age"))
        );
    }

    @Override
    protected AccountAggResult configResponseHook(SearchResponse resp) {
        AccountAggResult res = new AccountAggResult();
        res.setResp(resp);
        return res;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
