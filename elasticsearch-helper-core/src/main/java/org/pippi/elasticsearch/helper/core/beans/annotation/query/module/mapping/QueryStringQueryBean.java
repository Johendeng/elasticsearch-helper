package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * QueryStringQueryBean
 *
 * @author JohenTeng
 * @date 2022/4/29
 */
public class QueryStringQueryBean extends QueryBean<QueryStringQueryBuilder>{

    private transient static final String _SP = ":";

    private MultiMatchQueryBuilder.Type type;

    private String defaultField;

    private String field;

    private String fieldAndBoost;

    private String[] fieldAndBoosts;

    private String analyzer;

    private Operator defaultOperator;

    private FuzzinessEnum fuzziness;

    private Integer fuzzyMaxExpansions;

    private Integer fuzzyPrefixLength;

    private String fuzzyRewrite;

    private Boolean fuzzyTranspositions;

    private Boolean analyzeWildcard;

    private Boolean autoGenerateSynonymsPhraseQuery;

    private Boolean allowLeadingWildcard;

    private Boolean enablePositionIncrements;

    private Boolean escape;

    private Boolean lenient;

    private Integer phraseSlop;

    private Integer maxDeterminizedStates;

    private String quoteAnalyzer;

    private String quoteFieldSuffix;

    private Float tieBreaker;

    private ZoneId timeZone;

    private String minimumShouldMatch;

    private Float boost;

    @Override
    public void configQueryBuilder(QueryStringQueryBuilder queryBuilder) {
        if (StringUtils.isNotBlank(this.fieldAndBoost)) {
            String[] fieldBoostArr = this.fieldAndBoost.split(_SP);
            try {
                queryBuilder.field(fieldBoostArr[0], Float.parseFloat(fieldBoostArr[1]));
            } catch (Exception e) {
                throw new EsHelperConfigException("@QueryString's fieldAndBoost parse Error.", e);
            }
        }
        if (ArrayUtils.isNotEmpty(this.fieldAndBoosts)) {
            Map<String, Float> fieldAndBoostMap = Maps.newHashMap();
            Arrays.stream(this.fieldAndBoosts).forEach(config -> {
                String[] fieldBoostArr = config.split(_SP);
                try {
                    fieldAndBoostMap.put(fieldBoostArr[0], Float.parseFloat(fieldBoostArr[1]));
                } catch (Exception e) {
                    throw new EsHelperConfigException("@QueryString's fieldAndBoosts parse Error.", e);
                }
            });
            queryBuilder.fields(fieldAndBoostMap);
        }
        this.optional(this.type).ifPresent(queryBuilder::type);
        this.optional(this.defaultField).ifPresent(queryBuilder::defaultField);
        this.optional(this.field).ifPresent(queryBuilder::field);
        this.optional(this.allowLeadingWildcard).ifPresent(queryBuilder::allowLeadingWildcard);
        this.optional(this.analyzer).ifPresent(queryBuilder::analyzer);
        this.optional(this.analyzeWildcard).ifPresent(queryBuilder::analyzeWildcard);
        this.optional(this.autoGenerateSynonymsPhraseQuery).ifPresent(queryBuilder::autoGenerateSynonymsPhraseQuery);
        this.optional(this.defaultOperator).ifPresent(queryBuilder::defaultOperator);
        this.optional(this.enablePositionIncrements).ifPresent(queryBuilder::enablePositionIncrements);
        this.optional(this.escape).ifPresent(queryBuilder::escape);
        this.optional(this.fuzziness).ifPresent(fuzziness -> queryBuilder.fuzziness(fuzziness.getFuzziness()));
        this.optional(this.fuzzyMaxExpansions).ifPresent(queryBuilder::fuzzyMaxExpansions);
        this.optional(this.fuzzyPrefixLength).ifPresent(queryBuilder::fuzzyPrefixLength);
        this.optional(this.fuzzyRewrite).ifPresent(queryBuilder::fuzzyRewrite);
        this.optional(this.fuzzyTranspositions).ifPresent(queryBuilder::fuzzyTranspositions);
        this.optional(this.lenient).ifPresent(queryBuilder::lenient);
        this.optional(this.phraseSlop).ifPresent(queryBuilder::phraseSlop);
        this.optional(this.maxDeterminizedStates).ifPresent(queryBuilder::maxDeterminizedStates);
        this.optional(this.quoteAnalyzer).ifPresent(queryBuilder::quoteAnalyzer);
        this.optional(this.quoteFieldSuffix).ifPresent(queryBuilder::quoteFieldSuffix);
        this.optional(this.tieBreaker).ifPresent(queryBuilder::tieBreaker);
        this.optional(this.timeZone).ifPresent(queryBuilder::timeZone);
        this.optional(this.minimumShouldMatch).ifPresent(queryBuilder::minimumShouldMatch);
        this.optional(this.boost).ifPresent(queryBuilder::boost);
    }

    private <T>Optional<T> optional(T obj) {
        return Optional.ofNullable(obj).filter(
                o -> {
                    if (o instanceof String) {
                        return StringUtils.isNotBlank(o.toString());
                    }
                    return true;
                }
        );
    }
}
