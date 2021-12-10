package org.pippi.elasticsearch.helper.spring.config.hook;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.pippi.elasticsearch.helper.core.hook.UserHooks;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.AggAccountQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountAggResult;

/**
 * TestUserHooks
 *
 * @author dengtianjia@fiture.com
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
        res.setResp(resp);
        return res;
    });


}
