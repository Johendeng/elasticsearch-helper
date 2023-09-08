package org.pippi.elasticsearch.test.params;

import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;
import org.pippi.elasticsearch.test.result.AccountAggResult;

import java.util.List;
import java.util.Map;

/**
 * 实现自定义聚合，自定义结果返回
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsAnnQueryIndex(index = "account",
        fetch = "",
        model = QueryModel.BOOL)
public class AggAccountQueryParam extends HookQuery<AggAccountQueryParam, AccountAggResult> {

    private String city;


    @Override
    protected void configRequestHook(AbstractEsSession holder) {
        SearchSourceBuilder source = holder.getSource();
        holder.chain(QueryBuilders.termQuery("city", this.city));
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
