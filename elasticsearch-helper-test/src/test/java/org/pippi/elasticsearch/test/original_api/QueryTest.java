package org.pippi.elasticsearch.test.original_api;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.model.utils.JacksonUtils;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
//@Ignore
public class QueryTest {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void testQuery() throws IOException {
        SearchRequest req = new SearchRequest();
        SearchSourceBuilder source = new SearchSourceBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        source.query(bool);
        req.source(source);

        bool.must(QueryBuilders.termQuery("firstname.keyword", "Dale"));

        SearchResponse search = client.search(req, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.parseObjToJsonPretty(search));
    }


    @Test
    public void testNestdQuery() throws IOException {
        SearchRequest req = new SearchRequest();
        SearchSourceBuilder source = new SearchSourceBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        source.query(bool);
        req.source(source);

        bool.must(QueryBuilders.nestedQuery("detail_info", QueryBuilders.rangeQuery("detail_info.age").gt(20),
                ScoreMode.Total));

        SearchResponse search = client.search(req, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.parseObjToJsonPretty(search.getHits()));
    }

    @Test
    public void MovieTermQuery() {
        SearchRequest req = new SearchRequest();
        SearchSourceBuilder source = new SearchSourceBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        source.query(bool);
        req.source(source);
        bool.must(QueryBuilders.termQuery("id", 36915));


    }

    @Test
    public void timeFormatTest() {

        String timeStr = "1996-05-16T16:00:00.000Z";

        System.out.println(JacksonUtils.jsonToBean(timeStr, Date.class));
    }
}
