package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Term;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountAggResult;

import java.util.List;
import java.util.Map;

/**
 * 实现自定义聚合，自定义结果返回
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsQueryIndex(index = "account",
        fetch = "",
        model = QueryModel.BOOL)
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
        Aggregations aggOrg = resp.getAggregations();
        ParsedLongTerms ageAggTerms = aggOrg.get("_age");
        List<ParsedLongTerms.ParsedBucket> buckets = (List<ParsedLongTerms.ParsedBucket>)ageAggTerms.getBuckets();
        Map<String, Long> resMap = Maps.newHashMap();
        for (ParsedLongTerms.ParsedBucket bucket : buckets) {
            ParsedValueCount ageCount = bucket.getAggregations().get("_age_count");
            resMap.put(bucket.getKeyAsString(), ageCount.getValue());
        }
        res.setData(resMap);
        return res;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
