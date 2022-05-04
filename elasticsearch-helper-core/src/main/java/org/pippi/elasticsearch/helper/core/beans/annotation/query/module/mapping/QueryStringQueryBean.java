package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;

import java.time.ZoneId;
import java.util.Map;

/**
 * QueryStringQueryBean
 *
 * @author JohenTeng
 * @date 2022/4/29
 */
public class QueryStringQueryBean extends QueryBean<QueryStringQueryBuilder>{

    private transient static final String _SP = ":";

    private String defaultField;

    private String field;

    //Pair<String, Float>
    private String fieldAndBoost;
    //Map<String, Float>
    private String[] fieldAndBoosts;

    private String analyzer;

    private Operator defaultOperator;

    private FuzzinessEnum fuzzinessEnum;

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
        // todo
        if (StringUtils.isNotBlank(this.defaultField)) {
            queryBuilder.defaultField(this.defaultField);
        }
        if (StringUtils.isNotBlank(this.field)) {
            queryBuilder.field(this.field);
        }
        if (StringUtils.isNotBlank(this.fieldAndBoost)) {
            String[] fieldBoostArr = this.fieldAndBoost.split(_SP);
            try {
                queryBuilder.field(fieldBoostArr[0], Float.valueOf(fieldBoostArr[1]));
            } catch (Exception e) {
                throw new EsHelperConfigException("@QueryString's fieldAndBoost parse Error.", e);
            }
        }

        queryBuilder.fields();
        queryBuilder.allowLeadingWildcard();
        queryBuilder.analyzer();
        queryBuilder.analyzeWildcard();
        queryBuilder.autoGenerateSynonymsPhraseQuery();
        queryBuilder.defaultOperator();
        queryBuilder.enablePositionIncrements();
        queryBuilder.escape();
        queryBuilder.fuzziness();
        queryBuilder.fuzzyMaxExpansions();
        queryBuilder.fuzzyPrefixLength();
        queryBuilder.fuzzyRewrite();
        queryBuilder.fuzzyTranspositions();
        queryBuilder.lenient();
        queryBuilder.phraseSlop();
        queryBuilder.maxDeterminizedStates();
        queryBuilder.quoteAnalyzer();
        queryBuilder.quoteFieldSuffix();
        queryBuilder.tieBreaker();
        queryBuilder.timeZone();
        queryBuilder.minimumShouldMatch();
        queryBuilder.boost();
    }
}
