package org.pippi.elasticsearch.helper.core;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
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
@Deprecated
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
     *
     * @param index
     * @return
     */
    public EsSearchHelper init(String index) {
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
     *  默认最小匹配1个条件，与mysql的or保持一致
     *  切换逻辑连接符
     * @return
     */
    public EsSearchHelper should(){
        return this.should(1);
    }

    /**
     *  should 最小匹配的条件个数
     * @param minMatch
     * @return
     */
    public EsSearchHelper should(Integer minMatch){
        this.currentQueryBuilderList = bool.should();
        bool.minimumShouldMatch(minMatch);
        return this;
    }

    /**
     *  匹配一定比例的条件
     * @param percentStr
     * @return
     */
    public EsSearchHelper should(String percentStr){
        this.currentQueryBuilderList = bool.should();
        bool.minimumShouldMatch(percentStr);
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
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field, value);
        this.currentQueryBuilderList.add(termQueryBuilder);
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

    /**
     *  默认文档命中评分 1，
     *  默认使用分词器 ik_smart
     * @param field
     * @param value
     * @return
     */
    public EsSearchHelper match (String field, Object value) {
        return this.match(field, value, 1.0f, 50, null);
    }

    /**
     *
     * @param field
     * @param value
     * @param boost
     * @param analyzer
     * @return
     */
    public EsSearchHelper match(String field,
                                Object value,
                                Float boost,
                                Integer maxExpansions,
                                String analyzer) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(field, value);
        if(null != boost){
            matchQuery.boost(boost);
        }
        if(null != maxExpansions){
            matchQuery.maxExpansions(maxExpansions);
        }
        if(StringUtils.isNoneBlank(analyzer)){
            matchQuery.analyzer(analyzer);
        }
        this.currentQueryBuilderList.add(matchQuery);
        return this;
    }


    /**
     *
     * @param field
     * @param value
     * @return
     */
    public EsSearchHelper fuzzyQuery (String field, Object value) {
        return this.fuzzyQuery(field, value ,1f, Fuzziness.AUTO, 50, 0);
    }


    /**
     *  模糊搜索
     *
     * @param field
     * @param value
     * @param fuzzUnit 拼写错误的字符数
     * @param maxExpansions
     * @return
     */
    public EsSearchHelper fuzzyQuery(String field, Object value,
                                     Float boost,
                                     Fuzziness fuzzUnit,
                                     Integer maxExpansions,
                                     Integer maxPrefix) {
        FuzzyQueryBuilder fuzzyQueryBUilder = QueryBuilders.fuzzyQuery(field, value);
        if (fuzzUnit != null){ fuzzyQueryBUilder.fuzziness(fuzzUnit); }
        if (maxExpansions != null){ fuzzyQueryBUilder.maxExpansions(maxExpansions);}
        if (boost != null){ fuzzyQueryBUilder.boost(boost);}
        if (maxPrefix != null){ fuzzyQueryBUilder.prefixLength(maxPrefix);}
        this.currentQueryBuilderList.add(fuzzyQueryBUilder);
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

        if (type.equals(EsMeta.KEYWORD.getType())){



        }

        if (type.equals(EsMeta.TEXT.getType())) {




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

    public static EsSearchHelperBuilder builder(){
        return new EsSearchHelperBuilder();
    }

    public static class EsSearchHelperBuilder {

        public EsSearchHelper helper;

        public String indexName;

        public Class<? extends EsSearchHelper> clazz;

        public EsSearchHelperBuilder clazz(Class<? extends EsSearchHelper> clazz) {
            this.clazz = clazz;
            return this;
        }

        public EsSearchHelperBuilder index(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public <R extends EsSearchHelper>R build(){
            try {
                helper = clazz.getDeclaredConstructor().newInstance();
                helper.init(indexName);
            } catch (InstantiationException e) {
                throw new EsHelperQueryException("实例化异常", e);
            } catch (IllegalAccessException e) {
                throw new EsHelperQueryException("构造函数未设置为public", e);
            } catch (InvocationTargetException e) {
                throw new EsHelperQueryException("目标构造函数执行异常", e);
            } catch (NoSuchMethodException e) {
                throw new EsHelperQueryException("没有找到无参构造函数", e);
            }
            return (R)helper;
        }

    }







}
