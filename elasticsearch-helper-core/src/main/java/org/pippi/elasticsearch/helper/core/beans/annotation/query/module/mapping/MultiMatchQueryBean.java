package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.search.MatchQuery;
import org.pippi.elasticsearch.helper.core.beans.enums.Fuzzy;

import java.util.Collections;
import java.util.Map;

/**
 * ExtMultiMatch
 *
 * @author JohenTeng
 * @date 2021/9/24
 */
public class MultiMatchQueryBean extends QueryBean<MultiMatchQueryBuilder> {

    private static final String SEPARATOR = ",";
    private static final String MIDDLE = ":";

    private MultiMatchQueryBuilder.Type type;
    private MatchQuery.ZeroTermsQuery zeroTermsQuery;
    /**
     *  filed1:1.0,field2:2.0
     */
    private String boostFields;
    private boolean autoGenerateSynonymsPhraseQuery;
    private Fuzzy fuzziness;
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
                    .analyzer(analyzer)
                    .operator(operator)
                    .minimumShouldMatch(minimumShouldMatch)
                    .slop(slop);
        if (StringUtils.isNotBlank(boostFields)) {
            String[] sectionArr = boostFields.split(SEPARATOR);
            Map<String, Float> filedBoostMap = Maps.newHashMap();
            for (String section: sectionArr) {
                String[] cell = section.split(MIDDLE);
                String key = cell[0];
                Float val = Float.valueOf(cell[1]);
                filedBoostMap.put(key, val);
            }
            if (filedBoostMap.size() != 0) {
                queryBuilder.fields(filedBoostMap);
            }
        }
    }
}
