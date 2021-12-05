package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * user define A handler, then use this annotation to Call that handler
 *
 * author     JohenTeng
 * date      2021/9/30
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
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
