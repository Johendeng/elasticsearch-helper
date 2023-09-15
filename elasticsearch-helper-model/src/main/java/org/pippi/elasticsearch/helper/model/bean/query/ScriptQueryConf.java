package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * ScriptQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class ScriptQueryConf extends QueryConf<ScriptQueryBuilder> {

    ScriptType scriptType = ScriptType.INLINE;

    String lang = "painless";

    /**
     * define the script-ID (script-type is store) OR the script-code (script-type is inline)
     */
    String idOrCode;

    /**
     * Dose script has params
     * return
     */
    boolean hasParams ;

    public static ScriptQueryConf build() {
        return new ScriptQueryConf();
    }

    @Override
    public void configQueryBuilder(ScriptQueryBuilder queryBuilder) {

    }

    public ScriptQueryConf setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
        return this;
    }

    public ScriptQueryConf setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public ScriptQueryConf setIdOrCode(String idOrCode) {
        this.idOrCode = idOrCode;
        return this;
    }

    public ScriptQueryConf setHasParams(boolean hasParams) {
        this.hasParams = hasParams;
        return this;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public String getLang() {
        return lang;
    }


    public String getIdOrCode() {
        return idOrCode;
    }

    public boolean isHasParams() {
        return hasParams;
    }

}
