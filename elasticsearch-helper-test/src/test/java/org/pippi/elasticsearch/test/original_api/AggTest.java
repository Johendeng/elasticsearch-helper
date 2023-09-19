package org.pippi.elasticsearch.test.original_api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.model.resp.AggRes;
import org.pippi.elasticsearch.helper.model.utils.AggResponseVisitor;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author JohenDeng
 * @date 2023/9/18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
//@Ignore
public class AggTest {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void aggTest() throws IOException {
        SearchRequest req = new SearchRequest();
        SearchSourceBuilder source = new SearchSourceBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        source.query(bool);
        req.source(source);

//        source.aggregation(AggregationBuilders.terms("_gender").field("gender.keyword")
//                                .subAggregation(AggregationBuilders.percentileRanks("_age", new double[]{10, 20}).field("age")));

//        source.aggregation(AggregationBuilders.filters("_gender", QueryBuilders.termQuery("gender.keyword", "F"),
//                        QueryBuilders.termQuery("gender.keyword", "M"))
//                .subAggregation(AggregationBuilders.percentileRanks("_age", new double[]{10, 20}).field("age")));

//        source.aggregation(AggregationBuilders.filters(
//                "_gender", new FiltersAggregator.KeyedFilter("female", QueryBuilders.termQuery("gender.keyword", "F")),
//                new FiltersAggregator.KeyedFilter("man", QueryBuilders.termQuery("gender.keyword", "M")) )
//                .subAggregation(AggregationBuilders.percentileRanks("_age", new double[]{10, 20}).field("age")));

        source.aggregation(AggregationBuilders.range("_age_range").field("age")
                .addRange("r1", 10, 20)
                .addRange("r2",20, 30)
                .subAggregation(AggregationBuilders.count("_count").field("_id")));


        SearchResponse resp = client.search(req, RequestOptions.DEFAULT);
        AggRes aggRes = AggResponseVisitor.run(resp.getAggregations());
        System.out.println(SerializerUtils.parseObjToJsonPretty(aggRes));
    }
}
