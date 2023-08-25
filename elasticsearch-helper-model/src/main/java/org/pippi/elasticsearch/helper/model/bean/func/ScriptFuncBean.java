package org.pippi.elasticsearch.helper.model.bean.func;

import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.ScoreFuncBuilder;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.model.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public class ScriptFuncBean implements ScoreFuncBuilder {

    private ScriptType scriptType;

    private String lang;

    /**
     * define the script-ID (script-type is store) OR the script-code (script-type is inline)
     */
    private String idOrCode;

    private boolean hasParams;

    private float weight;

    private Map params = new HashMap();

    @Override
    public ScoreFunctionBuilder buildFuncScore() {
        Script script = new Script(this.getScriptType(), this.getLang(), this.getIdOrCode(), params);
        ScriptScoreFunctionBuilder scriptFunc = ScoreFunctionBuilders.scriptFunction(script);
        CommonUtils.filterIfPresent(weight, weight -> weight > 0, this::setWeight);
        return scriptFunc;
    }

    @Override
    public void builderExtend(EsQueryFieldBean fieldBean) {
        if (this.hasParams && fieldBean.getValue() instanceof Map && Objects.nonNull(fieldBean.getValue())) {
            params.putAll((Map) fieldBean.getValue());
        } else {
            throw new EsHelperQueryException("@ScriptQuery's [hasParams].value is TRUE the Field have to define as [Map.class] and can't be null");
        }
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
