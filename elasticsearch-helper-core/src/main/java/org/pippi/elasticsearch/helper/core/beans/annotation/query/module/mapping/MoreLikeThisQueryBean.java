package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;

/**
 * MoreLikeThisQueryBean
 *
 * author     JohenTeng
 * date      2021/9/28
 */
public class MoreLikeThisQueryBean extends QueryBean<MoreLikeThisQueryBuilder> {

    private String[] fields;

    private String analyzer;

    private float boostTerms = 0;

    private boolean failOnUnsupportedField = true;

    private boolean include = false;

    private int minTermFreq = 2;

    private int maxQueryTerms = 25;

    private int minDocFreq = 5;

    private int maxDocFreq = 2147483647;

    private int minWordLength = 0;

    private int maxWordLength = 0;

    private String minimumShouldMatch = "30%";

    private String[] stopWords = {};

    @Override
    public void configQueryBuilder(MoreLikeThisQueryBuilder queryBuilder) {
        queryBuilder.boostTerms(boostTerms)
                .failOnUnsupportedField(failOnUnsupportedField)
                .include(include)
                .minTermFreq(minTermFreq)
                .maxQueryTerms(maxQueryTerms)
                .minDocFreq(minDocFreq)
                .maxDocFreq(maxDocFreq)
                .minWordLength(minWordLength)
                .maxWordLength(maxWordLength)
                .minimumShouldMatch(minimumShouldMatch);
        if (StringUtils.isNotBlank(analyzer)) {
            queryBuilder.analyzer(analyzer);
        }
        if (ArrayUtils.isNotEmpty(stopWords)) {
            queryBuilder.stopWords(stopWords);
        }
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

}
