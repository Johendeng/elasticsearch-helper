package org.pippi.elasticsearch.helper.core;

import com.sun.tools.corba.se.idl.StringGen;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.beans.enums.Meta;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
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


    private SearchRequest request;
    private SearchSourceBuilder source;
    private BoolQueryBuilder bool;

    /**
     *  base query builder list
     */
    private List<QueryBuilder> baseQueryBuilderList;
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
        source = request.source();
        bool = QueryBuilders.boolQuery();
        source.query(bool);
        request.source(source);
        this.currentQueryBuilderList = bool.must();
        this.baseQueryBuilderList = currentQueryBuilderList;
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
        this.baseQueryBuilderList = currentQueryBuilderList;
        bool.minimumShouldMatch(1);
        return this;
    }

    public EsSearchHelper filter() {
        this.currentQueryBuilderList = bool.filter();
        this.baseQueryBuilderList = currentQueryBuilderList;
        return this;
    }

    public EsSearchHelper must() {
        this.currentQueryBuilderList = bool.must();
        this.baseQueryBuilderList = currentQueryBuilderList;
        return this;
    }

    public EsSearchHelper mustNot() {
        this.currentQueryBuilderList = bool.mustNot();
        this.baseQueryBuilderList = currentQueryBuilderList;
        return this;
    }

    public EsSearchHelper term(String field, Object value) {
        this.currentQueryBuilderList.add(QueryBuilders.termQuery(field, value));
        return this;
    }

    public EsSearchHelper terms(String field, Object[] values) {
        this.currentQueryBuilderList.add(QueryBuilders.termsQuery(field, values));
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




    /**
     *  use to realize a query-mode like
     *  must : {
     *      **:**,
     *      **:**,
     *      bool: {
     *          should: {
     *              **:**,
     *              **:**
     *          }
     *      }
     *  }
     * @return
     */
    public EsSearchHelper innerShould() {
        if (innerQueryBuilder == null) {
            innerQueryBuilder = QueryBuilders.boolQuery();
        }
        if (tempQueryBuilderList != null) {
            throw new EsHelperQueryException("inner query not end, cant initialize a new inner query-chain");
        }
        tempQueryBuilderList = innerQueryBuilder.should();
        baseQueryBuilderList.add(innerQueryBuilder);
        this.currentQueryBuilderList = tempQueryBuilderList;
        return this;
    }


    public EsSearchHelper innerEnd() {
        this.currentQueryBuilderList = this.baseQueryBuilderList;
        this.tempQueryBuilderList = null;
        this.innerQueryBuilder = null;
        return this;
    }


    public EsSearchHelper limit(int exclude, int size) {
        this.source.from(exclude).size(size);
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
