package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.ScriptQuery;

import java.util.Map;

/**
 * @author: JohenTeng
 * @date: 2022/5/8
 **/
@EsQueryIndex(index = "account", traceScore = true, size = 100)
public class ScriptQueryParam {

    @ScriptQuery(idOrCode = "(doc['age'].value%2 == 0)", hasParams = false)
    private Object scriptParams;

    @ScriptQuery(
            idOrCode = "doc['firstname.keyword'].value.startsWith(params.name)",
            hasParams = true
    )
    private Map<String, String> scriptMapParam;

    public Object getScriptParams() {
        return scriptParams;
    }

    public void setScriptParams(Object scriptParams) {
        this.scriptParams = scriptParams;
    }

    public Map<String, String> getScriptMapParam() {
        return scriptMapParam;
    }

    public void setScriptMapParam(Map<String, String> scriptMapParam) {
        this.scriptMapParam = scriptMapParam;
    }
}
