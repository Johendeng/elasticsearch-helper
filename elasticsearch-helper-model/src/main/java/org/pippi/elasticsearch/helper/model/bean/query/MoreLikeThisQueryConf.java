package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * MoreLikeThisQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class MoreLikeThisQueryConf extends QueryConf<MoreLikeThisQueryBuilder> {

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

    public static MoreLikeThisQueryConf build() {
        return new MoreLikeThisQueryConf();
    }

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

    public String[] fields() {
        return fields;
    }

    public MoreLikeThisQueryConf setFields(String[] fields) {
        this.fields = fields;
        return this;
    }

    public String analyzer() {
        return analyzer;
    }

    public MoreLikeThisQueryConf setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public float boostTerms() {
        return boostTerms;
    }

    public MoreLikeThisQueryConf setBoostTerms(float boostTerms) {
        this.boostTerms = boostTerms;
        return this;
    }

    public boolean failOnUnsupportedField() {
        return failOnUnsupportedField;
    }

    public MoreLikeThisQueryConf setFailOnUnsupportedField(boolean failOnUnsupportedField) {
        this.failOnUnsupportedField = failOnUnsupportedField;
        return this;
    }

    public boolean include() {
        return include;
    }

    public MoreLikeThisQueryConf setInclude(boolean include) {
        this.include = include;
        return this;
    }

    public int minTermFreq() {
        return minTermFreq;
    }

    public MoreLikeThisQueryConf setMinTermFreq(int minTermFreq) {
        this.minTermFreq = minTermFreq;
        return this;
    }

    public int maxQueryTerms() {
        return maxQueryTerms;
    }

    public MoreLikeThisQueryConf setMaxQueryTerms(int maxQueryTerms) {
        this.maxQueryTerms = maxQueryTerms;
        return this;
    }

    public int minDocFreq() {
        return minDocFreq;
    }

    public MoreLikeThisQueryConf setMinDocFreq(int minDocFreq) {
        this.minDocFreq = minDocFreq;
        return this;
    }

    public int maxDocFreq() {
        return maxDocFreq;
    }

    public MoreLikeThisQueryConf setMaxDocFreq(int maxDocFreq) {
        this.maxDocFreq = maxDocFreq;
        return this;
    }

    public int minWordLength() {
        return minWordLength;
    }

    public MoreLikeThisQueryConf setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
        return this;
    }

    public int maxWordLength() {
        return maxWordLength;
    }

    public MoreLikeThisQueryConf setMaxWordLength(int maxWordLength) {
        this.maxWordLength = maxWordLength;
        return this;
    }

    public String minimumShouldMatch() {
        return minimumShouldMatch;
    }

    public MoreLikeThisQueryConf setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public String[] stopWords() {
        return stopWords;
    }

    public MoreLikeThisQueryConf setStopWords(String[] stopWords) {
        this.stopWords = stopWords;
        return this;
    }
}
