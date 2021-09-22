package org.pippi.elasticsearch.helper.sample;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.spring.annotation.EnableEsHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * SampleApplication
 *
 * @author johen
 * @date 2021/9/17
 */
@EnableEsHelper
@SpringBootApplication(scanBasePackages = "com.sun")
public class SampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SampleApplication.class, args);
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));
    }


}
