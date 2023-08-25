package org.pippi.elasticsearch.helper.core.beans.query;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;


import java.util.Map;

/**
 * ExtMultiMatch
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class MultiMatchQueryBean extends QueryBean<MultiMatchQueryBuilder> {

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

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public MultiMatchQueryBuilder.Type getType() {
        return type;
    }

    public void setType(MultiMatchQueryBuilder.Type type) {
        this.type = type;
    }

    public ZeroTermsQueryOption getZeroTermsQuery() {
        return zeroTermsQuery;
    }

    public void setZeroTermsQuery(ZeroTermsQueryOption zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
    }

    public String[] getBoostFields() {
        return boostFields;
    }

    public void setBoostFields(String[] boostFields) {
        this.boostFields = boostFields;
    }

    public boolean isAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public void setAutoGenerateSynonymsPhraseQuery(boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
    }

    public FuzzinessEnum getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
    }

    public boolean isFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public void setFuzzyTranspositions(boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
    }

    public boolean isLenient() {
        return lenient;
    }

    public void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public int getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
    }

    public int getMaxExpansions() {
        return maxExpansions;
    }

    public void setMaxExpansions(int maxExpansions) {
        this.maxExpansions = maxExpansions;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public int getSlop() {
        return slop;
    }

    public void setSlop(int slop) {
        this.slop = slop;
    }
}
