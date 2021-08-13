//package org.pippi.elasticsearch.helper.core.elasticsearch.engine.holder;
//
//import com.fiture.content.search.common.elasticsearch.enums.EsQueryModel;
//import com.fiture.content.search.common.elasticsearch.enums.LogicConnector;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//import java.util.List;
//
///**
// * 描述 持有es查询相关的对象实例，进行统一调用和管理
// *
// * @author dengtianjia@fiture.com
// * @date 2021/8/8
// */
//public abstract class AbstractEsRequestHolder<T extends QueryBuilder> {
//
//	private String indexName;
//
//	private SearchRequest request;
//
//	private SearchSourceBuilder source;
//
//	private T queryBuilder;
//
//	private List<QueryBuilder> currentQueryBuilderList ;
//
//	/**
//	 *
//	 * @param index
//	 * @return
//	 */
//	public AbstractEsRequestHolder init(String index) {
//		this.indexName = index;
//		request = new SearchRequest(index);
//		source = new SearchSourceBuilder();
//		this.defineQueryBuilder();
//		source.query(queryBuilder);
//		request.source(source);
//		this.defineDefaultLogicConnector();
//		return this;
//	}
//
//	/**
//	 *  改变逻辑连接符
//	 * @param logicKey
//	 * @return
//	 */
//	public abstract AbstractEsRequestHolder changeLogicConnector(LogicConnector logicKey);
//
//	/**
//	 *  定义默认的逻辑连接集合
//	 */
//	protected abstract void defineDefaultLogicConnector();
//
//
//
//
//	public AbstractEsRequestHolder chain(QueryBuilder queryCell){
//		this.currentQueryBuilderList.add(queryCell);
//		return this;
//	}
//	/**
//	 *  定义查询构造对象 是boolQuery, dis-max 等
//	 */
//	protected abstract void defineQueryBuilder();
//
//	public String getIndexName() {
//		return indexName;
//	}
//
//	public void setIndexName(String indexName) {
//		this.indexName = indexName;
//	}
//
//	public SearchRequest getRequest() {
//		return request;
//	}
//
//	public void setRequest(SearchRequest request) {
//		this.request = request;
//	}
//
//	public SearchSourceBuilder getSource() {
//		return source;
//	}
//
//	public void setSource(SearchSourceBuilder source) {
//		this.source = source;
//	}
//
//	public T getQueryBuilder() {
//		return queryBuilder;
//	}
//
//	public void setQueryBuilder(T queryBuilder) {
//		this.queryBuilder = queryBuilder;
//	}
//
//	public List<QueryBuilder> getCurrentQueryBuilderList() {
//		return currentQueryBuilderList;
//	}
//
//	public void setCurrentQueryBuilderList(List<QueryBuilder> currentQueryBuilderList) {
//		this.currentQueryBuilderList = currentQueryBuilderList;
//	}
//
//	public static EsRequestHolderBuilder builder(){
//		return new EsRequestHolderBuilder();
//	}
//
//	public static class EsRequestHolderBuilder {
//
//		public String indexName;
//		public EsQueryModel esQueryModel;
//
//		public EsRequestHolderBuilder define(String indexName, EsQueryModel model) {
//			this.indexName = indexName;
//			this.esQueryModel = model;
//			return this;
//		}
//
//		public <R extends AbstractEsRequestHolder>R build(){
//			if (StringUtils.isBlank(indexName) || esQueryModel == null){
//				throw new RuntimeException("索引及查询模式不能为空");
//			}
//			if (esQueryModel == EsQueryModel.BOOL) {
//				BoolEsRequestHolder boolEsRequestHolder = new BoolEsRequestHolder();
//				boolEsRequestHolder.init(indexName);
//				return (R)boolEsRequestHolder;
//			}
//			throw new RuntimeException("暂不支持该查询模式");
//		}
//	}
//
//}
