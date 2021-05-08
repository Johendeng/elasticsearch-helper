package com.poet.elasticsearch.helper;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/3/12
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class EsSearchHelper {

    private static final Logger log = LoggerFactory.getLogger(EsSearchHelper.class);


    private String indexName;
    private RestHighLevelClient client;


    private SearchRequest request;
    private SearchSourceBuilder source;
    private BoolQueryBuilder bool;

    /**
     *  current search about collections, default is MUST
     */
    private List<QueryBuilder> currentQueryBuilderList;



    protected EsSearchHelper init(RestHighLevelClient client, String index) {
        this.indexName = index;
        this.client = client;
        request = new SearchRequest(index);
        source = request.source();
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



    // change the logic connects of action request
    public EsSearchHelper should(){
        this.currentQueryBuilderList = bool.should();
        bool.minimumShouldMatch();
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

    public EsSearchHelper term(String column, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.termQuery(column, value));
        return this;
    }

    public EsSearchHelper terms(String column, Object[] values) {
        this.currentQueryBuilderList.add(QueryBuilders.termQuery(column, values));
        return this;
    }

    public EsSearchHelper match (String column, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.matchQuery(column, value));
        return this;
    }

    public EsSearchHelper fuzzyQuery (String column, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.fuzzyQuery(column, value));
        return this;
    }


    public EsSearchHelper like(String column, String value) {


        return this;
    }


    public EsSearchHelper agg(AggregationBuilder aggBuilder) {
        source.aggregation(aggBuilder);
        return this;
    }





}
