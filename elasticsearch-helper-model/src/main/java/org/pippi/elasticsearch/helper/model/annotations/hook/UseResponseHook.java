package org.pippi.elasticsearch.helper.model.annotations.hook;

import java.lang.annotation.*;

/**
 * Project Name:elasticsearch-helper
 * File Name:ResponseHook
 * Package Name:org.pippi.elasticsearch.helper.beans.annotation
 * @date 2021/7/21 00:33
 * @author   JohenTeng
 * Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UseResponseHook {

    String value();

}
