package org.pippi.elasticsearch.helper.beans.annotation;

import java.lang.annotation.*;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsQueryIndex
 * Package Name:org.pippi.elasticsearch.helper.beans.annotation
 * Date:2021/7/18 15:26
 * Author:dengtianjia
 * Description:
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryIndex {

    String index() ;

}
