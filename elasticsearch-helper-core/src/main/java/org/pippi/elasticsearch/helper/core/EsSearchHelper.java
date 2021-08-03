package org.pippi.elasticsearch.helper.core;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentParserUtils;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.beans.enums.Meta;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 *  应该支持 function_score, script
 *  支持es的 种子评分字段   衰减函数
 *  支持 script_score
 *
 *
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/3/12
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class EsSearchHelper {

    private static final Logger log = LoggerFactory.getLogger(EsSearchHelper.class);


    private String indexName;


    private SearchRequest request;
    private SearchSourceBuilder source;
    private BoolQueryBuilder bool;

    /**
     *  current search about collections, default is MUST
     */
    private List<QueryBuilder> currentQueryBuilderList;

    /**
     *  inner query builder
     */
    private BoolQueryBuilder innerQueryBuilder;
    /**
     *  middle search-builder-list rel
     */
    private List<QueryBuilder> tempQueryBuilderList;

    protected EsSearchHelper init(String index) {
        this.indexName = index;
        request = new SearchRequest(index);
        source = new SearchSourceBuilder();
        bool = QueryBuilders.boolQuery();
        source.query(bool);
        request.source(source);
        this.currentQueryBuilderList = bool.must();
        return this;
    }

    public SearchRequest getRequest() {
        return request;
    }

    public SearchSourceBuilder getSource() {
        return source;
    }

    public BoolQueryBuilder getBool() {
        return bool;
    }


    /**
     *  切换逻辑连接符
     * @return
     */
    public EsSearchHelper should(){
        this.currentQueryBuilderList = bool.should();
        bool.minimumShouldMatch(1);
        return this;
    }

    public EsSearchHelper filter() {
        this.currentQueryBuilderList = bool.filter();
        return this;
    }

    public EsSearchHelper must() {
        this.currentQueryBuilderList = bool.must();
        return this;
    }

    public EsSearchHelper mustNot() {
        this.currentQueryBuilderList = bool.mustNot();
        return this;
    }

    /**
     *  关键字匹配查询，默认得分为1
     * @param field
     * @param value
     * @return
     */
    public EsSearchHelper term(String field, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.termQuery(field, value));
        return this;
    }

    /**
     *  关键字匹配查询，自定义得分
     * @param field
     * @param value
     * @param boost
     * @return
     */
    public EsSearchHelper term(String field, Object value, Float boost){
        TermQueryBuilder termQuery = QueryBuilders.termQuery(field, value).boost(boost);
        this.currentQueryBuilderList.add(termQuery);
        return this;
    }


    public EsSearchHelper terms(String field, Object[] values) {
        this.currentQueryBuilderList.add(QueryBuilders.termsQuery(field, values));
        return this;
    }



    public EsSearchHelper terms(String field, Object[] values, Float boost) {
        this.currentQueryBuilderList.add(QueryBuilders.termsQuery(field, values).boost(boost));
        return this;
    }



    public EsSearchHelper match (String field, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.matchQuery(field, value));
        return this;
    }

    public EsSearchHelper fuzzyQuery (String field, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.fuzzyQuery(field, value));
        return this;
    }

    public EsSearchHelper gt(String field, Number value) {
        this.currentQueryBuilderList.add(QueryBuilders.rangeQuery(field).gt(value));
        return this;
    }

    public EsSearchHelper gte(String field, Number value) {
        this.currentQueryBuilderList.add(QueryBuilders.rangeQuery(field).gte(value));
        return this;
    }

    public EsSearchHelper lt(String field, Number value) {
        this.currentQueryBuilderList.add(QueryBuilders.rangeQuery(field).lt(value));
        return this;
    }

    public EsSearchHelper lte(String field, Number value) {
        this.currentQueryBuilderList.add(QueryBuilders.rangeQuery(field).lte(value));
        return this;
    }

    public EsSearchHelper like(String field, String value) {

        this.currentQueryBuilderList.add(QueryBuilders.wildcardQuery(field, "*" + value + "*"));
        return this;
    }


    public EsSearchHelper like(String field, String value, String type) {

        if (type.equals(Meta.KEYWORD.getType())){



        }

        if (type.equals(Meta.TEXT.getType())) {




        }

        if (StringUtils.isBlank(type)) {



        }

        return this;
    }


    public EsSearchHelper limit(int exclude, int size) {
        this.source.from(exclude).size(size);
        return this;
    }

    /**
     *  脚本查询
     * @return
     */
    public EsSearchHelper script(String scriptStr){
        Script script = new Script(scriptStr);
        this.currentQueryBuilderList.add(QueryBuilders.scriptQuery(script));
        return this;
    }

    /**
     *  Script(ScriptType type, String lang, String idOrCode, Map<String, Object> params)
     * @return
     */
    public EsSearchHelper script(ScriptType type,
                                 String lang,
                                 String idOrCode,
                                 Map<String, Object> params) {
        Script script = new Script(type, lang, idOrCode, params);
        this.currentQueryBuilderList.add(QueryBuilders.scriptQuery(script));
        return this;
    }

    /**
     * 自定义评分方法
     * @return
     */
    public EsSearchHelper functionScoreQuery () {
        QueryBuilders.functionScoreQuery(QueryBuilders.termQuery("",""));
        return this;
    }




    public EsSearchHelper agg(AggregationBuilder aggBuilder) {
        source.aggregation(aggBuilder);
        return this;
    }

    public EsSearchHelper chain(QueryBuilder queryBuilder) {
        this.currentQueryBuilderList.add(queryBuilder);
        return this;
    }



}
