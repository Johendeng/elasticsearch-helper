package org.pippi.elasticsearch.helper.model.annotations.mapper.base;



import org.pippi.elasticsearch.helper.model.ScoreFuncBuilder;

import java.lang.annotation.*;

/**
 * @author JohenTeng
 * @date 2022/8/11
 **/
@Inherited
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FuncQuery {

    Class<? extends ScoreFuncBuilder> value();
}
