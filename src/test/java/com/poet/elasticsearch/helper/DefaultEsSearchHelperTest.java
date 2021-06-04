package com.poet.elasticsearch.helper;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/3/24
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class DefaultEsSearchHelperTest {

    private RestHighLevelClient client;

    private static final String _TEST_INDEX = "demo";

    @Before
    public void init(){
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.42.132", 9200)));
    }



    @Test
    public void testEsSearchLike() throws IOException {
//        EsSearchHelper.build(client, _TEST_INDEX);
        EsSearchHelper helper = new DefaultEsSearchHelper();
        helper.init(client, _TEST_INDEX);
        helper.like("name", "ang");
//        helper.terms("age", new Object[]{34,32,15});

        SearchResponse search = client.search(helper.getRequest(), RequestOptions.DEFAULT);
        System.out.println(EsResponseParseHelper.read(search));
    }

    @Test
    public void testEsApi() {

        SearchScrollRequest request = new SearchScrollRequest();



    }




}