package org.pippi.elasticsearch.helper.core.beans.annotation.query;

import java.lang.annotation.*;

/**
 * GroupHandles
 *   define multi query-type for a field
 * @author johenTeng
 * @date 2021/9/17
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface MultiQueryField {

    Base[] value();

}
