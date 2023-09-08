package org.pippi.elasticsearch.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.spring.EnableEsHelper;
import org.pippi.elasticsearch.helper.spring.EsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@EnableEsHelper
@SpringBootApplication
public class EsHelperSampleApplication {

	@Bean
	@EsClient(name = "client1", primary = true)
	public RestHighLevelClient restHighLevelClient1(){
		return new RestHighLevelClient(RestClient.builder(
				new HttpHost("localhost", 9200),
				new HttpHost("localhost", 9201),
				new HttpHost("localhost", 9202)
		));
	}

	@Bean
	@EsClient(name = "client2", primary = false)
	public RestHighLevelClient restHighLevelClient2(){
		return new RestHighLevelClient(RestClient.builder(
				new HttpHost("localhost", 9200),
				new HttpHost("localhost", 9201),
				new HttpHost("localhost", 9202)
		));
	}

	public static void main(String[] args) {
		SpringApplication.run(EsHelperSampleApplication.class, args);
	}

}
