package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.SimpleQueryStringFlag;
import org.pippi.elasticsearch.helper.core.utils.CommonUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SimpleQueryString
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
public class SimpleQueryStringBean extends QueryBean<SimpleQueryStringBuilder>{

    private transient static final String _SP = ":";

    private String field;

    private String fieldAndBoost;

    private String[] fieldAndBoosts;

    private String analyzer;

    private Boolean analyzeWildcard;

    private Boolean autoGenerateSynonymsPhraseQuery;

    private Operator defaultOperator;

    private Integer fuzzyPrefixLength;

    private Integer fuzzyMaxExpansions;

    private Boolean lenient;

    private String minimumShouldMatch;

    private String quoteFieldSuffix;

    private Float boost;

    private SimpleQueryStringFlag[] flags;

    @Override
    public void configQueryBuilder(SimpleQueryStringBuilder queryBuilder) {
        CommonUtils.optional(this.field).ifPresent(queryBuilder::field);
        CommonUtils.optional(this.fieldAndBoost).ifPresent(fb -> {
            String[] fbArr = fb.split(_SP);
            queryBuilder.field(fbArr[0], Float.parseFloat(fbArr[1]));
        });
        CommonUtils.optional(this.fieldAndBoosts).ifPresent(fbs -> {
            Map<String, Float> floatBoostMap = Arrays.stream(fbs).map(fb -> fb.split(_SP))
                    .filter(fbArr -> ArrayUtils.isNotEmpty(fbArr) && fbArr.length == 2)
                    .collect(Collectors.toMap(fbArr -> fbArr[0], fbArr -> Float.parseFloat(fbArr[1]), (v1, v2) -> v1));
            queryBuilder.fields(floatBoostMap);
        });
        CommonUtils.optional(this.analyzer).ifPresent(queryBuilder::analyzer);
        CommonUtils.optional(this.analyzeWildcard).ifPresent(queryBuilder::analyzeWildcard);
        CommonUtils.optional(this.autoGenerateSynonymsPhraseQuery).ifPresent(queryBuilder::autoGenerateSynonymsPhraseQuery);
        CommonUtils.optional(this.defaultOperator).ifPresent(queryBuilder::defaultOperator);
        CommonUtils.optional(this.fuzzyPrefixLength).ifPresent(queryBuilder::fuzzyPrefixLength);
        CommonUtils.optional(this.fuzzyMaxExpansions).ifPresent(queryBuilder::fuzzyMaxExpansions);
        CommonUtils.optional(this.lenient).ifPresent(queryBuilder::lenient);
        CommonUtils.optional(this.minimumShouldMatch).ifPresent(queryBuilder::minimumShouldMatch);
        CommonUtils.optional(this.quoteFieldSuffix).ifPresent(queryBuilder::quoteFieldSuffix);
        CommonUtils.optional(this.boost).ifPresent(queryBuilder::boost);
        CommonUtils.optional(this.flags).ifPresent(queryBuilder::flags);
    }
}
