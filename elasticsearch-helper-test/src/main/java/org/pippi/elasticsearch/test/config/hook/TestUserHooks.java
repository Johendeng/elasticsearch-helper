package org.pippi.elasticsearch.test.config.hook;

import com.google.common.collect.Maps;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.pippi.elasticsearch.helper.core.hook.UserHooks;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.AggAccountQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountAggResult;

import java.util.List;
import java.util.Map;

/**
 * TestUserHooks
 *
 * @author JohenTeng
 * @date 2021/12/10
 */
public class TestUserHooks implements UserHooks {

    public static RequestHook<AggAccountQueryParam> aggReqHook = (holder, param) -> {
        SearchSourceBuilder source = holder.getSource();
        source.aggregation(
                AggregationBuilders.terms("_age").field("age").subAggregation(
                        AggregationBuilders.count("_age_count").field("age"))
        );
        return holder;
    };

    public static ResponseHook<AccountAggResult> aggResRespHook = (resp -> {
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
    });
}
