package org.pippi.elasticsearch.helper.core.holder;


import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.HighLightBean;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.config.GlobalEsQueryConfig;
import org.pippi.elasticsearch.helper.core.utils.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *  hold es request bean
 *
 * author     JohenTeng
 * date      2021/8/8
 */
public abstract class AbstractEsRequestHolder<T extends QueryBuilder> {


	private static final Map<QueryModel, Class<? extends AbstractEsRequestHolder>> HOLDER_CLAZZ_MAP = Maps.newHashMap();

	static {
		/**
		 *  implements *EsRequestHolder load on this place
		 */
		AbstractEsRequestHolder.regisHolder(QueryModel.BOOL, BoolEsRequestHolder.class);
		AbstractEsRequestHolder.regisHolder(QueryModel.DIS_MAX, DisMaxEsRequestHolder.class);

	}

	private String indexName;

	private SearchRequest request;

	private SearchSourceBuilder source;

	private T queryBuilder;

	private List<QueryBuilder> currentQueryBuilderList ;

	/**
	 *
	 * @param index
	 * return
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

	public AbstractEsRequestHolder simpleInitialize() {
		this.defineQueryBuilder();
		this.defineDefaultLogicConnector();
		return this;
	}

	public static void regisHolder(QueryModel model, Class<? extends AbstractEsRequestHolder> clazz) {
		HOLDER_CLAZZ_MAP.put(model, clazz);
	}

	/**
	 *  change logic connector
	 * @param connector
	 * return
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
		public String[] fetchFields;
		public String[] excludeFields;
		public float minScore;
		public boolean traceScore;
		public HighLightBean highLightBean;

		public EsRequestHolderBuilder config(EsQueryIndexBean indexBean) {
			this.indexName = indexBean.getIndexName();
			this.esQueryModel = indexBean.getEsQueryModel();
			this.fetchFields = indexBean.getFetchFields();
			this.minScore = indexBean.getMinScore();
			this.traceScore = indexBean.getTraceScore();
			this.highLightBean = indexBean.getHighLight();
			return this;
		}

		public EsRequestHolderBuilder indexName(String indexName) {
			this.indexName = indexName;
			return this;
		}

		public EsRequestHolderBuilder queryModel(QueryModel model){
			this.esQueryModel = model;
			return this;
		}

		public EsRequestHolderBuilder minScore(float minScore){
			this.minScore = minScore;
			return this;
		}

		public <R extends AbstractEsRequestHolder>R build(){
			if (StringUtils.isBlank(indexName) || esQueryModel == null){
				throw new RuntimeException("index and query model cant be null");
			}
			Class<? extends AbstractEsRequestHolder> targetClazz = HOLDER_CLAZZ_MAP.get(esQueryModel);
			if (Objects.nonNull(targetClazz)) {
				AbstractEsRequestHolder holder = ReflectionUtils.newInstance(targetClazz);
				holder.init(indexName);
				SearchSourceBuilder source = holder.getSource();
				if (ArrayUtils.isNotEmpty(this.fetchFields) || ArrayUtils.isNotEmpty(this.excludeFields)){
					source.fetchSource(this.fetchFields, this.excludeFields);
				}
				if (Objects.nonNull(highLightBean)) {
					HighlightBuilder highlightBuilder = GlobalEsQueryConfig.highLight(highLightBean.getHighLightKey());
					for (String field : highLightBean.getFields()) {
						source.highlighter(highlightBuilder.field(field));
					}
				}
				if (minScore > 0) {
					holder.getSource().minScore(minScore);
				}
				if (traceScore) {
					holder.getSource().trackScores(traceScore);
				}
				return (R)holder;
			}
			throw new RuntimeException("un-support this query model");
		}


		public <R extends AbstractEsRequestHolder>R simpleBuild(){
			if (esQueryModel == null){
				throw new RuntimeException("index and query model cant be null");
			}
			Class<? extends AbstractEsRequestHolder> targetClazz = HOLDER_CLAZZ_MAP.get(esQueryModel);
			if (Objects.nonNull(targetClazz)) {
				AbstractEsRequestHolder holder = ReflectionUtils.newInstance(targetClazz);
				holder.simpleInitialize();
				return (R)holder;
			}
			throw new RuntimeException("unsupport this query model");
		}
	}

}
