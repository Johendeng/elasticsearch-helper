package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import org.pippi.elasticsearch.helper.model.bean.query.FuzzyQueryConf;
import org.pippi.elasticsearch.helper.model.bean.query.MatchQueryConf;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface SearchFunc<F, Children> extends Serializable {

    Children size(int size);

    Children config(int size, float minScore, boolean traceScore);

    /**
     * term
     */
    default Children term(F field, Object val) {
        return term(true, field, val);
    }

    Children term(boolean condition, F field, Object val);

    default Children term(F field, Object val, float boost) {
        return term(true, field, val, boost);
    }

    Children term(boolean condition, F field, Object val, float boost);

    /**
     * terms
     */
    default Children terms(F field, Object... val) {
        return terms(true, field, val);
    }

    Children terms(boolean condition, F field, Object... val);

    default Children terms(F field, float boost, Object... val) {
        return terms(true, field, boost, val);
    }

    Children terms(boolean condition, F field, float boost, Object... val);

    /**
     * match
     */
    default Children match(F field, Object val) {
        return match(true, field, val);
    }

    Children match(boolean condition, F field, Object val);

    default Children match(F field, Object val, float boost) {
        return match(true, field, val, boost);
    }

    Children match(boolean condition, F field, Object val, float boost);

    default Children match(F field, Object val, float boost, MatchQueryConf config) {
        return match(true, field, val, boost, config);
    }

    Children match(boolean condition, F field, Object val, float boost, MatchQueryConf config);

    /**
     * fuzzy
     */
    default Children fuzzy(F field, Object val) {
        return fuzzy(true, field, val);
    }

    Children fuzzy(boolean condition, F field, Object val);

    default Children fuzzy(F field, Object val, float boost) {
        return fuzzy(true, field, val, boost);
    }

    Children fuzzy(boolean condition, F field, Object val, float boost);

    default Children fuzzy(F field, Object val, float boost, FuzzyQueryConf config) {
        return fuzzy(true, field, val, boost, config);
    }

    Children fuzzy(boolean condition, F field, Object val, float boost, FuzzyQueryConf config);



    // todo ...



}
