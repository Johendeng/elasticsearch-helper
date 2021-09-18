package org.pippi.elasticsearch.helper.sample;

import org.pippi.elasticsearch.helper.spring.annotation.EnableEsHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

}
