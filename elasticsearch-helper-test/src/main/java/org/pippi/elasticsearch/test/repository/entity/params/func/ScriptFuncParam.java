package org.pippi.elasticsearch.test.repository.entity.params.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore_ScriptFunc;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;

import java.util.Map;

/**
 * @author: JohenTeng
 * @date: 2022/8/17
 **/
@EsAnnQueryIndex(index = "account", model = QueryModel.FUNC_SCORE, traceScore = true,
        funcScore = @FuncScore(boostMode = CombineFunction.SUM)
)
public class ScriptFuncParam {

    @Match(fuzziness = FuzzinessEnum.ONE)
    private String address;

    @FuncScore_ScriptFunc(
            idOrCode = "doc['firstname.keyword'].value.startsWith(params.name)? 10: 0",
            hasParams = true
    )
    private Map<String, String> scriptMapParam;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, String> getScriptMapParam() {
        return scriptMapParam;
    }

    public void setScriptMapParam(Map<String, String> scriptMapParam) {
        this.scriptMapParam = scriptMapParam;
    }
}
