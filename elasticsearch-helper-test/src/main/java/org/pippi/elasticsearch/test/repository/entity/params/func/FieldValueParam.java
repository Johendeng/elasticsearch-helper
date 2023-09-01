package org.pippi.elasticsearch.test.repository.entity.params.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore_FieldValueFactor;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;

/**
 * @author: JohenTeng
 * @date: 2022/8/17
 **/
@EsAnnQueryIndex(index = "account", model = QueryModel.FUNC_SCORE,
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
