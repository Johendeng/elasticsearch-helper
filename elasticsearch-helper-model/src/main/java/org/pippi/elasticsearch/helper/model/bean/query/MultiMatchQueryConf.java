package org.pippi.elasticsearch.helper.model.bean.query;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;


import java.util.Map;

/**
 * ExtMultiMatch
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class MultiMatchQueryConf extends QueryConf<MultiMatchQueryBuilder> {

    private static final String MIDDLE = ":";

    private String[] fields;
    private MultiMatchQueryBuilder.Type type;
    private ZeroTermsQueryOption zeroTermsQuery;
    /**
     *  filed1:1.0,field2:2.0
     */
    private String[] boostFields;
    private boolean autoGenerateSynonymsPhraseQuery;
    private FuzzinessEnum fuzziness;
    private boolean fuzzyTranspositions;
    private boolean lenient;
    private int prefixLength;
    private int maxExpansions;
    private String analyzer;
    private Operator operator;
    private String minimumShouldMatch;
    private int slop;

    @Override
    public void configQueryBuilder(MultiMatchQueryBuilder queryBuilder) {
        queryBuilder.type(type)
                    .zeroTermsQuery(zeroTermsQuery)
                    .autoGenerateSynonymsPhraseQuery(autoGenerateSynonymsPhraseQuery)
                    .fuzziness(fuzziness.getFuzziness())
                    .fuzzyTranspositions(fuzzyTranspositions)
                    .lenient(lenient)
                    .prefixLength(prefixLength)
                    .maxExpansions(maxExpansions)
                    .operator(operator)
                    .slop(slop);
        if (StringUtils.isNotBlank(analyzer)) {
            queryBuilder.analyzer(analyzer);
        }
        if (StringUtils.isNotBlank(minimumShouldMatch)) {
            queryBuilder.minimumShouldMatch(minimumShouldMatch);
        }
        if (ArrayUtils.isNotEmpty(boostFields)) {
            Map<String, Float> filedBoostMap = Maps.newHashMap();
            for (String section : boostFields) {
                String[] cell = section.split(MIDDLE);
                String key = cell[0];
                Float val = Float.valueOf(cell[1]);
                filedBoostMap.put(key, val);
            }
            queryBuilder.fields(filedBoostMap);
        }
    }

    public String[] fields() {
        return fields;
    }

    public MultiMatchQueryConf setFields(String[] fields) {
        this.fields = fields;
        return this;
    }

    public MultiMatchQueryBuilder.Type type() {
        return type;
    }

    public MultiMatchQueryConf setType(MultiMatchQueryBuilder.Type type) {
        this.type = type;
        return this;
    }

    public ZeroTermsQueryOption zeroTermsQuery() {
        return zeroTermsQuery;
    }

    public MultiMatchQueryConf setZeroTermsQuery(ZeroTermsQueryOption zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
        return this;
    }

    public String[] boostFields() {
        return boostFields;
    }

    public MultiMatchQueryConf setBoostFields(String[] boostFields) {
        this.boostFields = boostFields;
        return this;
    }

    public boolean autoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public MultiMatchQueryConf setAutoGenerateSynonymsPhraseQuery(boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        return this;
    }

    public FuzzinessEnum fuzziness() {
        return fuzziness;
    }

    public MultiMatchQueryConf setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public boolean fuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public MultiMatchQueryConf setFuzzyTranspositions(boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
        return this;
    }

    public boolean lenient() {
        return lenient;
    }

    public MultiMatchQueryConf setLenient(boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public int prefixLength() {
        return prefixLength;
    }

    public MultiMatchQueryConf setPrefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public int maxExpansions() {
        return maxExpansions;
    }

    public MultiMatchQueryConf setMaxExpansions(int maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public String analyzer() {
        return analyzer;
    }

    public MultiMatchQueryConf setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public Operator operator() {
        return operator;
    }

    public MultiMatchQueryConf setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public String minimumShouldMatch() {
        return minimumShouldMatch;
    }

    public MultiMatchQueryConf setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public int slop() {
        return slop;
    }

    public MultiMatchQueryConf setSlop(int slop) {
        this.slop = slop;
        return this;
    }
}
