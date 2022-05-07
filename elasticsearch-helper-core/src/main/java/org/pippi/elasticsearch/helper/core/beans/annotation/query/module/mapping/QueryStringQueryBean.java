package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;

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

    private String timeZone;

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
                    if (o.getClass().isArray()) {
                        return ArrayUtils.isEmpty((Object[]) o);
                    }
                    if (o instanceof String) {
                        return StringUtils.isNotBlank(o.toString());
                    }
                    return true;
                }
        );
    }

    public MultiMatchQueryBuilder.Type getType() {
        return type;
    }

    public void setType(MultiMatchQueryBuilder.Type type) {
        this.type = type;
    }

    public String getDefaultField() {
        return defaultField;
    }

    public void setDefaultField(String defaultField) {
        this.defaultField = defaultField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldAndBoost() {
        return fieldAndBoost;
    }

    public void setFieldAndBoost(String fieldAndBoost) {
        this.fieldAndBoost = fieldAndBoost;
    }

    public String[] getFieldAndBoosts() {
        return fieldAndBoosts;
    }

    public void setFieldAndBoosts(String[] fieldAndBoosts) {
        this.fieldAndBoosts = fieldAndBoosts;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public Operator getDefaultOperator() {
        return defaultOperator;
    }

    public void setDefaultOperator(Operator defaultOperator) {
        this.defaultOperator = defaultOperator;
    }

    public FuzzinessEnum getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
    }

    public Integer getFuzzyMaxExpansions() {
        return fuzzyMaxExpansions;
    }

    public void setFuzzyMaxExpansions(Integer fuzzyMaxExpansions) {
        this.fuzzyMaxExpansions = fuzzyMaxExpansions;
    }

    public Integer getFuzzyPrefixLength() {
        return fuzzyPrefixLength;
    }

    public void setFuzzyPrefixLength(Integer fuzzyPrefixLength) {
        this.fuzzyPrefixLength = fuzzyPrefixLength;
    }

    public String getFuzzyRewrite() {
        return fuzzyRewrite;
    }

    public void setFuzzyRewrite(String fuzzyRewrite) {
        this.fuzzyRewrite = fuzzyRewrite;
    }

    public Boolean getFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public void setFuzzyTranspositions(Boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
    }

    public Boolean getAnalyzeWildcard() {
        return analyzeWildcard;
    }

    public void setAnalyzeWildcard(Boolean analyzeWildcard) {
        this.analyzeWildcard = analyzeWildcard;
    }

    public Boolean getAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public void setAutoGenerateSynonymsPhraseQuery(Boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
    }

    public Boolean getAllowLeadingWildcard() {
        return allowLeadingWildcard;
    }

    public void setAllowLeadingWildcard(Boolean allowLeadingWildcard) {
        this.allowLeadingWildcard = allowLeadingWildcard;
    }

    public Boolean getEnablePositionIncrements() {
        return enablePositionIncrements;
    }

    public void setEnablePositionIncrements(Boolean enablePositionIncrements) {
        this.enablePositionIncrements = enablePositionIncrements;
    }

    public Boolean getEscape() {
        return escape;
    }

    public void setEscape(Boolean escape) {
        this.escape = escape;
    }

    public Boolean getLenient() {
        return lenient;
    }

    public void setLenient(Boolean lenient) {
        this.lenient = lenient;
    }

    public Integer getPhraseSlop() {
        return phraseSlop;
    }

    public void setPhraseSlop(Integer phraseSlop) {
        this.phraseSlop = phraseSlop;
    }

    public Integer getMaxDeterminizedStates() {
        return maxDeterminizedStates;
    }

    public void setMaxDeterminizedStates(Integer maxDeterminizedStates) {
        this.maxDeterminizedStates = maxDeterminizedStates;
    }

    public String getQuoteAnalyzer() {
        return quoteAnalyzer;
    }

    public void setQuoteAnalyzer(String quoteAnalyzer) {
        this.quoteAnalyzer = quoteAnalyzer;
    }

    public String getQuoteFieldSuffix() {
        return quoteFieldSuffix;
    }

    public void setQuoteFieldSuffix(String quoteFieldSuffix) {
        this.quoteFieldSuffix = quoteFieldSuffix;
    }

    public Float getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(Float tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public Float getBoost() {
        return boost;
    }

    public void setBoost(Float boost) {
        this.boost = boost;
    }
}
