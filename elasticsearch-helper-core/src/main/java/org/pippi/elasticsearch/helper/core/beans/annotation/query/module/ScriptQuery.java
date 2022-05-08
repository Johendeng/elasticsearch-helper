package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.lang.annotation.*;

/**
 * {@link org.elasticsearch.script.Script} description:
 * param type     The {@link ScriptType}.
 * param lang     The language for this {@link org.elasticsearch.script.Script} if the {@link ScriptType} is {@link ScriptType#INLINE}.
 *                For {@link ScriptType#STORED} scripts this should be null, but can
 *                be specified to access scripts stored as part of the stored scripts deprecated API.
 * param idOrCode The id for this {@link org.elasticsearch.script.Script} if the {@link ScriptType} is {@link ScriptType#STORED}.
 *                The code for this {@link org.elasticsearch.script.Script} if the {@link ScriptType} is {@link ScriptType#INLINE}.
 * param options  The map of compiler options for this {@link org.elasticsearch.script.Script} if the {@link ScriptType}
 *                is {@link ScriptType#INLINE}, {@code null} otherwise.
 * param params   The user-defined params to be bound for script execution.
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptQuery {

    Base value() default @Base(meta = EsMeta.COMPLEX);

    ScriptType scriptType() default ScriptType.INLINE;

    String lang() default org.elasticsearch.script.Script.DEFAULT_SCRIPT_LANG;

    String idOrCode() default "";

    /**
     * Dose script has params
     */
    boolean hasParams() default false;
}
