package org.pippi.elasticsearch.helper.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable ElasticSearch Handler
 *
 * @author     JohenTeng
 * @date      2021/9/18
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EsHelperAutoConfiguration.class})
@ComponentScan(basePackages = {"org.pippi.elasticsearch.helper.spring"})
public @interface EnableEsHelper {
}
