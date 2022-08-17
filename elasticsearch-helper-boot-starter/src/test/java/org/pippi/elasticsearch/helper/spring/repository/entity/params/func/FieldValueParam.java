package org.pippi.elasticsearch.helper.spring.repository.entity.params.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.FuncScore;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Match;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.FuncScore_FieldValueFactor;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

/**
 * @author: JohenTeng
 * @date: 2022/8/17
 **/
@EsQueryIndex(index = "account", model = QueryModel.FUNC_SCORE,
        traceScore = true,
        funcScore = @FuncScore(boostMode = CombineFunction.SUM)
)
public class FieldValueParam {

    @Match(fuzziness = FuzzinessEnum.TWO)
    @FuncScore_FieldValueFactor(field = "account_number", modifier = FieldValueFactorFunction.Modifier.LOG1P, missing = 1)
    private String city;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
