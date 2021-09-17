package org.pippi.elasticsearch.helper.core.beans.annotation.hook;

import java.lang.annotation.*;

/**
 * Project Name:elasticsearch-helper
 * File Name:ResponseHook
 * Package Name:org.pippi.elasticsearch.helper.beans.annotation
 * Date:2021/7/21 00:33
 * @Author:dengtianjia
 * Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ResponseHook {

    String value();

}
