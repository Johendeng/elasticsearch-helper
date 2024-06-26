package com.example.eshelper.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.spring.EsClient;
import org.pippi.elasticsearch.helper.spring.EsHelperCustomerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author JohenDeng
 * @date 2023/9/14
 **/
@Configuration
public class EsHelperConfig implements EsHelperCustomerConfig {


    @Bean
    @EsClient(name = "client1", primary = true)
    public RestHighLevelClient restHighLevelClient1(){
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200),
                new HttpHost("localhost", 9201),
                new HttpHost("localhost", 9202)
        ));
    }

    @Override
    public Map<String, Supplier<HighlightBuilder>> declareHighLight() {
        return null;
    }

    @Override
    public Map<String, RequestOptions> declareRequestOpt() {
        return null;
    }
}
