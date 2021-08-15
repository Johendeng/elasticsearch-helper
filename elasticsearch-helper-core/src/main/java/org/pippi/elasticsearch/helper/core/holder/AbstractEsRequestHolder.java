package org.pippi.elasticsearch.helper.core.holder;


import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.beans.enums.QueryModel;

import java.util.List;

/**
 *  hold es request bean
 *
 * @author JohenTeng
 * @date 2021/8/8
 */
public abstract class AbstractEsRequestHolder<T extends QueryBuilder> {

	private String indexName;

	private SearchRequest request;

	private SearchSourceBuilder source;

	private T queryBuilder;

	private List<QueryBuilder> currentQueryBuilderList ;

	/**
	 *
	 * @param index
	 * @return
	 */
	public AbstractEsRequestHolder init(String index) {
		this.indexName = index;
		request = new SearchRequest(index);
		source = new SearchSourceBuilder();
		this.defineQueryBuilder();
		source.query(queryBuilder);
		request.source(source);
		this.defineDefaultLogicConnector();
		return this;
	}

	/**
	 *  change logic connector
	 * @param connector
	 * @return
	 */
	public abstract AbstractEsRequestHolder changeLogicConnector(EsConnector connector);

	/**
	 *  define the default logic connector
	 */
	protected abstract void defineDefaultLogicConnector();




	public AbstractEsRequestHolder chain(QueryBuilder queryCell){
		this.currentQueryBuilderList.add(queryCell);
		return this;
	}
	/**
	 *  定义查询构造对象 是boolQuery, dis-max 等
	 */
	protected abstract void defineQueryBuilder();

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public SearchRequest getRequest() {
		return request;
	}

	public void setRequest(SearchRequest request) {
		this.request = request;
	}

	public SearchSourceBuilder getSource() {
		return source;
	}

	public void setSource(SearchSourceBuilder source) {
		this.source = source;
	}

	public T getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(T queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public List<QueryBuilder> getCurrentQueryBuilderList() {
		return currentQueryBuilderList;
	}

	public void setCurrentQueryBuilderList(List<QueryBuilder> currentQueryBuilderList) {
		this.currentQueryBuilderList = currentQueryBuilderList;
	}

	public static EsRequestHolderBuilder builder(){
		return new EsRequestHolderBuilder();
	}

	public static class EsRequestHolderBuilder {

		public String indexName;
		public QueryModel esQueryModel;

		public EsRequestHolderBuilder define(String indexName, QueryModel model) {
			this.indexName = indexName;
			this.esQueryModel = model;
			return this;
		}

		public <R extends AbstractEsRequestHolder>R build(){
			if (StringUtils.isBlank(indexName) || esQueryModel == null){
				throw new RuntimeException("index and query model cant be null");
			}
			if (esQueryModel == QueryModel.BOOL) {
				BoolEsRequestHolder boolEsRequestHolder = new BoolEsRequestHolder();
				boolEsRequestHolder.init(indexName);
				return (R)boolEsRequestHolder;
			}
			throw new RuntimeException("unsupport this query model");
		}
	}

}
