package org.pippi.elasticsearch.test.repository.entity.params.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore_ExponentialDecay;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;

/**
 * @author: JohenTeng
 * @date: 2022/8/16
 **/
@EsQueryBean(index = "account", model = QueryModel.FUNC_SCORE, traceScore = true,
        funcScore = @FuncScore(boostMode = CombineFunction.SUM)
)
public class ExponentialDecayParam {

    @Match
    private String lastname;

    @FuncScore_ExponentialDecay(scale = "50", offset = "100")
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
