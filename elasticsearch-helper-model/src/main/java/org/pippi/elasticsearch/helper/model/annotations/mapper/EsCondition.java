package org.pippi.elasticsearch.helper.model.annotations.mapper;



import org.pippi.elasticsearch.helper.model.EsConditionHandle;

import java.lang.annotation.*;

/**
 * project  elasticsearch-helper
 * packages   org.pippi.elasticsearch.helper.core.beans.annotation.query
 * @date     2021/12/3
 * @author   JohenTeng
 * email    1078481395@qq.com
 **/
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsCondition {

    Class<? extends EsConditionHandle> value();

}
