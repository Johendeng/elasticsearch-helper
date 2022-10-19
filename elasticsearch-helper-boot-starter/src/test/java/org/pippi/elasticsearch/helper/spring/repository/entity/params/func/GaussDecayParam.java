package org.pippi.elasticsearch.helper.spring.repository.entity.params.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.FuncScore;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Match;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.func.FuncScore_GaussDecay;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

/**
 * @author: JohenTeng
 * @date: 2022/8/12
 **/
@EsQueryIndex(index = "account", model = QueryModel.FUNC_SCORE, traceScore = true,
        funcScore = @FuncScore(boostMode = CombineFunction.SUM)
)
public class GaussDecayParam {

    @Match
    private String lastname;

    @FuncScore_GaussDecay(scale = "50", offset = "100")
    private Integer balance;


    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
