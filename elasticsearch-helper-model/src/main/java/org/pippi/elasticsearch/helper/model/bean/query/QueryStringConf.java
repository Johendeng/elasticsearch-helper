package org.pippi.elasticsearch.helper.model.bean.query;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.model.utils.CommonUtils;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

import java.util.Arrays;
import java.util.Map;

/**
 * QueryStringQueryBean
 *
 * @author JohenTeng
 * @date 2022/4/29
 */
public class QueryStringConf extends QueryConf<QueryStringQueryBuilder> {

    private static final String _SP = ":";

    private String defaultField;

    private String field;

    private String fieldAndBoost;

    private String[] fieldAndBoosts;

    /**
     * for lambda
     */
    private Map<String, Float> fieldAndBoostMap;

    private MultiMatchQueryBuilder.Type type = MultiMatchQueryBuilder.Type.BEST_FIELDS;

    private String analyzer;

    private Operator defaultOperator = Operator.OR;

    private FuzzinessEnum fuzziness = FuzzinessEnum.AUTO;

    private Integer fuzzyMaxExpansions = 50;

    private Integer fuzzyPrefixLength = 0;

    private String fuzzyRewrite;

    private Boolean fuzzyTranspositions = true;

    private Boolean analyzeWildcard = false;

    private Boolean allowLeadingWildcard = true;

    private Boolean autoGenerateSynonymsPhraseQuery = true;


    private Boolean enablePositionIncrements = true;

    private Boolean escape = false;

    private Boolean lenient = false;

    private Integer phraseSlop = 0;

    private Integer maxDeterminizedStates = 10000;

    private String quoteAnalyzer;

    private String quoteFieldSuffix;

    private Float tieBreaker = 0.0f;

    private String timeZone = "+08:00";

    private String minimumShouldMatch;

    private Float boost = 1.0f;

    public static QueryStringConf build() {
        return new QueryStringConf();
    }

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
        CommonUtils.optional(this.type).ifPresent(queryBuilder::type);
        CommonUtils.optional(this.defaultField).ifPresent(queryBuilder::defaultField);
        CommonUtils.optional(this.field).ifPresent(queryBuilder::field);
        CommonUtils.optional(this.allowLeadingWildcard).ifPresent(queryBuilder::allowLeadingWildcard);
        CommonUtils.optional(this.analyzer).ifPresent(queryBuilder::analyzer);
        CommonUtils.optional(this.analyzeWildcard).ifPresent(queryBuilder::analyzeWildcard);
        CommonUtils.optional(this.autoGenerateSynonymsPhraseQuery).ifPresent(queryBuilder::autoGenerateSynonymsPhraseQuery);
        CommonUtils.optional(this.defaultOperator).ifPresent(queryBuilder::defaultOperator);
        CommonUtils.optional(this.enablePositionIncrements).ifPresent(queryBuilder::enablePositionIncrements);
        CommonUtils.optional(this.escape).ifPresent(queryBuilder::escape);
        CommonUtils.optional(this.fuzziness).ifPresent(fuzziness -> queryBuilder.fuzziness(fuzziness.getFuzziness()));
        CommonUtils.optional(this.fuzzyMaxExpansions).ifPresent(queryBuilder::fuzzyMaxExpansions);
        CommonUtils.optional(this.fuzzyPrefixLength).ifPresent(queryBuilder::fuzzyPrefixLength);
        CommonUtils.optional(this.fuzzyRewrite).ifPresent(queryBuilder::fuzzyRewrite);
        CommonUtils.optional(this.fuzzyTranspositions).ifPresent(queryBuilder::fuzzyTranspositions);
        CommonUtils.optional(this.lenient).ifPresent(queryBuilder::lenient);
        CommonUtils.optional(this.phraseSlop).ifPresent(queryBuilder::phraseSlop);
        CommonUtils.optional(this.maxDeterminizedStates).ifPresent(queryBuilder::maxDeterminizedStates);
        CommonUtils.optional(this.quoteAnalyzer).ifPresent(queryBuilder::quoteAnalyzer);
        CommonUtils.optional(this.quoteFieldSuffix).ifPresent(queryBuilder::quoteFieldSuffix);
        CommonUtils.optional(this.tieBreaker).ifPresent(queryBuilder::tieBreaker);
        CommonUtils.optional(this.timeZone).ifPresent(queryBuilder::timeZone);
        CommonUtils.optional(this.minimumShouldMatch).ifPresent(queryBuilder::minimumShouldMatch);
        CommonUtils.optional(this.boost).ifPresent(queryBuilder::boost);
    }

    public MultiMatchQueryBuilder.Type type() {
        return type;
    }

    public QueryStringConf setType(MultiMatchQueryBuilder.Type type) {
        this.type = type;
        return this;
    }

    public String defaultField() {
        return defaultField;
    }

    public QueryStringConf setDefaultField(String defaultField) {
        this.defaultField = defaultField;
        return this;
    }

    public String field() {
        return field;
    }

    public QueryStringConf setField(String field) {
        this.field = field;
        return this;
    }

    public String fieldAndBoost() {
        return fieldAndBoost;
    }

    public QueryStringConf setFieldAndBoost(String fieldAndBoost) {
        this.fieldAndBoost = fieldAndBoost;
        return this;
    }

    public String[] fieldAndBoosts() {
        return fieldAndBoosts;
    }

    public QueryStringConf setFieldAndBoosts(String[] fieldAndBoosts) {
        this.fieldAndBoosts = fieldAndBoosts;
        return this;
    }

    public String analyzer() {
        return analyzer;
    }

    public QueryStringConf setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public Operator defaultOperator() {
        return defaultOperator;
    }

    public QueryStringConf setDefaultOperator(Operator defaultOperator) {
        this.defaultOperator = defaultOperator;
        return this;
    }

    public FuzzinessEnum fuzziness() {
        return fuzziness;
    }

    public QueryStringConf setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public Integer fuzzyMaxExpansions() {
        return fuzzyMaxExpansions;
    }

    public QueryStringConf setFuzzyMaxExpansions(Integer fuzzyMaxExpansions) {
        this.fuzzyMaxExpansions = fuzzyMaxExpansions;
        return this;
    }

    public Integer fuzzyPrefixLength() {
        return fuzzyPrefixLength;
    }

    public QueryStringConf setFuzzyPrefixLength(Integer fuzzyPrefixLength) {
        this.fuzzyPrefixLength = fuzzyPrefixLength;
        return this;
    }

    public String fuzzyRewrite() {
        return fuzzyRewrite;
    }

    public QueryStringConf setFuzzyRewrite(String fuzzyRewrite) {
        this.fuzzyRewrite = fuzzyRewrite;
        return this;
    }

    public Boolean fuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public QueryStringConf setFuzzyTranspositions(Boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
        return this;
    }

    public Boolean analyzeWildcard() {
        return analyzeWildcard;
    }

    public QueryStringConf setAnalyzeWildcard(Boolean analyzeWildcard) {
        this.analyzeWildcard = analyzeWildcard;
        return this;
    }

    public Boolean autoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public QueryStringConf setAutoGenerateSynonymsPhraseQuery(Boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        return this;
    }

    public Boolean allowLeadingWildcard() {
        return allowLeadingWildcard;
    }

    public QueryStringConf setAllowLeadingWildcard(Boolean allowLeadingWildcard) {
        this.allowLeadingWildcard = allowLeadingWildcard;
        return this;
    }

    public Boolean enablePositionIncrements() {
        return enablePositionIncrements;
    }

    public QueryStringConf setEnablePositionIncrements(Boolean enablePositionIncrements) {
        this.enablePositionIncrements = enablePositionIncrements;
        return this;
    }

    public Boolean escape() {
        return escape;
    }

    public QueryStringConf setEscape(Boolean escape) {
        this.escape = escape;
        return this;
    }

    public Boolean lenient() {
        return lenient;
    }

    public QueryStringConf setLenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public Integer phraseSlop() {
        return phraseSlop;
    }

    public QueryStringConf setPhraseSlop(Integer phraseSlop) {
        this.phraseSlop = phraseSlop;
        return this;
    }

    public Integer maxDeterminizedStates() {
        return maxDeterminizedStates;
    }

    public QueryStringConf setMaxDeterminizedStates(Integer maxDeterminizedStates) {
        this.maxDeterminizedStates = maxDeterminizedStates;
        return this;
    }

    public String quoteAnalyzer() {
        return quoteAnalyzer;
    }

    public QueryStringConf setQuoteAnalyzer(String quoteAnalyzer) {
        this.quoteAnalyzer = quoteAnalyzer;
        return this;
    }

    public String quoteFieldSuffix() {
        return quoteFieldSuffix;
    }

    public QueryStringConf setQuoteFieldSuffix(String quoteFieldSuffix) {
        this.quoteFieldSuffix = quoteFieldSuffix;
        return this;
    }

    public Float tieBreaker() {
        return tieBreaker;
    }

    public QueryStringConf setTieBreaker(Float tieBreaker) {
        this.tieBreaker = tieBreaker;
        return this;
    }

    public String timeZone() {
        return timeZone;
    }

    public QueryStringConf setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public String minimumShouldMatch() {
        return minimumShouldMatch;
    }

    public QueryStringConf setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public Float boost() {
        return boost;
    }

    public QueryStringConf setBoost(Float boost) {
        this.boost = boost;
        return this;
    }

    public Map<String, Float> fieldAndBoostMap() {
        return fieldAndBoostMap;
    }

    public QueryStringConf setFieldAndBoostMap(Map<String, Float> fieldAndBoostMap) {
        this.fieldAndBoostMap = fieldAndBoostMap;
        return this;
    }
}
