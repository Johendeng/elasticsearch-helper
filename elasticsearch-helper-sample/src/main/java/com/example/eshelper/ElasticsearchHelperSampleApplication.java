package com.example.eshelper;

import org.pippi.elasticsearch.helper.spring.EnableEsHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEsHelper
@SpringBootApplication
public class ElasticsearchHelperSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchHelperSampleApplication.class, args);
	}

}
