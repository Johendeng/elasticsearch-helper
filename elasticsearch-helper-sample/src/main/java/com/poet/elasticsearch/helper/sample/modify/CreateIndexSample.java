package com.poet.elasticsearch.helper.sample.modify;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.sample.modify
 * date:    2021/7/13
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class CreateIndexSample {

    private static RestHighLevelClient client;


    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("local.dev1", 9200)));
    }


    public static void main(String[] args) throws IOException {


    }

    static void init() throws IOException {
        Map<String, Object> demo = new HashMap<>();
        demo.put("name", "zhangsan");
        demo.put("age", 12);
        demo.put("des", "zhe shi yi ge test");
        IndexRequest req = new IndexRequest("test1");
        req.source(demo, XContentType.JSON);
        client.index(req, RequestOptions.DEFAULT);
    }







}
