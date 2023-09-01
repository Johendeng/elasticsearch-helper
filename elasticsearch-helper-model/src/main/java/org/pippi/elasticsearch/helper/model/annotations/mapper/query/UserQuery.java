package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * user define A handler, then use this annotation to Call that handler
 *
 * @author     JohenTeng
 * @date      2021/9/30
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserQuery {

    /**
     * @Base( queryType )  queryType: have to define by user,
     * it's importance
     *
     * Ex:
     * @UserQuery(@Base( queryType = "userDemoHandler"), ...)
     * private Object targetParam;
     *
     * @EsQueryHandle(queryType = "userDemoHandler")
     * public Class UserDemoHandler extends AbstractQueryHandler { ... }
     */
    Base value();

}
