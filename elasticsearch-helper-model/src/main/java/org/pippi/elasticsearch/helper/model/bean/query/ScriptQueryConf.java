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

    ScriptType scriptType;

    String lang;

    /**
     * define the script-ID (script-type is store) OR the script-code (script-type is inline)
     */
    String idOrCode;

    /**
     * Dose script has params
     * return
     */
    boolean hasParams;

    @Override
    public void configQueryBuilder(ScriptQueryBuilder queryBuilder) {

    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getIdOrCode() {
        return idOrCode;
    }

    public void setIdOrCode(String idOrCode) {
        this.idOrCode = idOrCode;
    }

    public boolean isHasParams() {
        return hasParams;
    }

    public void setHasParams(boolean hasParams) {
        this.hasParams = hasParams;
    }
}
