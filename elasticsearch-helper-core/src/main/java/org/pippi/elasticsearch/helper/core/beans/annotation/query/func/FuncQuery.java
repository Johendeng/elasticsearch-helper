package org.pippi.elasticsearch.helper.core.beans.annotation.query.func;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.func.mapping.ScoreFuncBuilder;

import java.lang.annotation.*;

/**
 * @author: JohenTeng
 * @date: 2022/8/11
 **/
@Inherited
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FuncQuery {

    Class<? extends ScoreFuncBuilder> value();
}
