package org.pippi.elasticsearch.helper.spring;

import java.lang.annotation.*;

/**
 * @author JohenDeng
 * @date 2023/9/8
 **/
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsClient {

    String name();

    boolean primary();
}
