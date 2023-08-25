package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;


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

    Base value() default @Base;

    ScriptType scriptType() default ScriptType.INLINE;

    String lang() default org.elasticsearch.script.Script.DEFAULT_SCRIPT_LANG;

    /**
     * script or script-id define in elasticsearch-server,
     * 脚本 或者 定义在es服务器中的脚本id
     */
    String idOrCode();

    /**
     * Dose script has params
     * 如果该脚本不需要传参数，则该注解修饰的字段不需要赋值
     * 如果有参数请将该字段定义为 Map<String, Object> 其中map.value只能为 String/基本类型
     */
    boolean hasParams();
}
