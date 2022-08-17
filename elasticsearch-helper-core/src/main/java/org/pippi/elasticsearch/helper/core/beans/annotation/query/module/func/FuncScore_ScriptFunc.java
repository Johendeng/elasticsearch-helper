package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping.ScriptFuncBean;

import java.lang.annotation.*;

/**
 * @author: JohenTeng
 * @date: 2022/7/19
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FuncQuery(ScriptFuncBean.class)
public @interface FuncScore_ScriptFunc {

    String field() default "";

    ScriptType scriptType() default ScriptType.INLINE;

    String lang() default Script.DEFAULT_SCRIPT_LANG;

    /**
     * script or script-id define in elasticsearch-server,
     * 脚本 或者 定义在es服务器中的脚本id
     */
    String idOrCode();

    boolean hasParams() default false;

    float weight() default 0.0f;
}
