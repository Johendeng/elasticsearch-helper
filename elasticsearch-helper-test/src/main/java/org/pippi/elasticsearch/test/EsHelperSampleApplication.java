package org.pippi.elasticsearch.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.spring.EnableEsHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableEsHelper
@SpringBootApplication
public class EsHelperSampleApplication {

	@Bean
	public RestHighLevelClient restHighLevelClient(){
		return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));
	}

	public static void main(String[] args) {
		SpringApplication.run(EsHelperSampleApplication.class, args);
	}

}
