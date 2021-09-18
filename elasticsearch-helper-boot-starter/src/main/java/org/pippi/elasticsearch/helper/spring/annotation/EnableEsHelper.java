package org.pippi.elasticsearch.helper.spring.annotation;

import org.pippi.elasticsearch.helper.spring.EsHelperAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableEsHelper
 *
 * @author dengtianjia@fiture.com
 * @date 2021/9/18
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({EsHelperAutoConfiguration.class})
@ComponentScan(basePackages = {"org.pippi.elasticsearch.helper.spring"})
public @interface EnableEsHelper {
}
