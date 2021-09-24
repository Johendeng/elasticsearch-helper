package org.pippi.elasticsearch.helper.core.beans.annotation.query.ext;

import java.lang.annotation.*;

/**
 * ExtMuilteMatch
 *
 * @author JohenTeng
 * @date 2021/9/23
 */
@Ext
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtMultiMatch {
}
