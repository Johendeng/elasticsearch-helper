package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * MatchPhraseQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class MatchPhraseQueryConf extends QueryConf<MatchPhraseQueryBuilder> {

    private int slop;

    private ZeroTermsQueryOption zeroTermsQuery;

    private String analyzer;

    @Override
    public void configQueryBuilder(MatchPhraseQueryBuilder queryBuilder) {
        queryBuilder.slop(slop).zeroTermsQuery(zeroTermsQuery);
        if (StringUtils.isNotBlank(analyzer)) {
            queryBuilder.analyzer(analyzer);
        }
    }

}
