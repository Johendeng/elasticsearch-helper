package org.pippi.elasticsearch.test.original_api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author JohenDeng
 * @date 2023/9/18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
@Ignore
public class AggTest {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void aggTest() {
        SearchRequest req = new SearchRequest();
        SearchSourceBuilder source = new SearchSourceBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        source.query(bool);
        req.source(source);



    }
}
