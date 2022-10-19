package org.pippi.elasticsearch.helper.core.session;


import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.config.GlobalEsQueryConfig;
import org.pippi.elasticsearch.helper.core.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  hold es request bean
 *
 * @author     JohenTeng
 * @date      2021/8/8
 */
@SuppressWarnings("all")
public abstract class AbstractEsSession<T extends QueryBuilder> {

	protected static final Logger log = LoggerFactory.getLogger(AbstractEsSession.class);

	private static final Map<QueryModel, Class<? extends AbstractEsSession>> HOLDER_CLAZZ_MAP = Maps.newHashMap();

	static {
		/**
		 *  implements *EsRequestHolder load on this place
		 */
		AbstractEsSession.regisHolder(QueryModel.BOOL, BoolEsSession.class);
		AbstractEsSession.regisHolder(QueryModel.DIS_MAX, DisMaxEsSession.class);
		AbstractEsSession.regisHolder(QueryModel.FUNC_SCORE, FuncScoreEsSession.class);

	}

	private String indexName;

	private SearchRequest request;

	private SearchSourceBuilder source;

	private T queryBuilder;

	private List<QueryBuilder> currentQueryBuilderList ;

	private EsQueryIndexBean indexConfig;

	/**
	 *
	 * @param index
	 * return
	 */
	public AbstractEsSession init(String index) {
		this.indexName = index;
		request = new SearchRequest(index);
		source = new SearchSourceBuilder();
		this.defineQueryBuilder();
		source.query(queryBuilder);
		request.source(source);
		this.defineDefaultLogicConnector();
		return this;
	}

	public static void regisHolder(QueryModel model, Class<? extends AbstractEsSession> clazz) {
		HOLDER_CLAZZ_MAP.put(model, clazz);
	}

	/**
	 *  change logic connector
	 * @param connector
	 * return
	 */
	public abstract AbstractEsSession changeLogicConnector(EsConnector connector);

	/**
	 *  define the default logic connector
	 */
	protected abstract void defineDefaultLogicConnector();

	public AbstractEsSession chain(QueryBuilder queryCell){
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

		private EsQueryIndexBean indexConfig;

		public EsRequestHolderBuilder config(EsQueryIndexBean indexBean) {
			this.indexConfig = indexBean;
			return this;
		}

		public <R extends AbstractEsSession> R build(){
			if (StringUtils.isBlank(indexConfig.getIndexName()) || indexConfig.getEsQueryModel() == null){
				throw new RuntimeException("index and query model cant be null");
			}
			Class<? extends AbstractEsSession> targetClazz = HOLDER_CLAZZ_MAP.get(indexConfig.getEsQueryModel());
			if (Objects.isNull(targetClazz)) {
				throw new RuntimeException("un-support this query model");
			}
			AbstractEsSession holder = ReflectionUtils.newInstance(targetClazz);
			holder.init(indexConfig.getIndexName());
			SearchSourceBuilder source = holder.getSource();
			if (ArrayUtils.isNotEmpty(indexConfig.getFetchFields()) || ArrayUtils.isNotEmpty(indexConfig.getExcludeFields())){
				source.fetchSource(indexConfig.getFetchFields(), indexConfig.getExcludeFields());
			}
			if (Objects.nonNull(indexConfig.getHighLight())) {
				HighlightBuilder highlightBuilder = GlobalEsQueryConfig.highLight(indexConfig.getHighLight().getHighLightKey());
				if (Objects.isNull(highlightBuilder)) {
					log.error("can't find highlight-config: {}", indexConfig.getHighLight().getHighLightKey());
				} else {
					for (String field : indexConfig.getHighLight().getFields()) {
						source.highlighter(highlightBuilder.field(field));
					}
				}
			}
			if (indexConfig.getMinScore() > 0) {
				holder.getSource().minScore(indexConfig.getMinScore());
			}
			if (indexConfig.getTraceScore()) {
				holder.getSource().trackScores(indexConfig.getTraceScore());
			}
			holder.getSource().size(indexConfig.getSize());
			holder.setIndexConfig(this.indexConfig);
			return (R) holder;
		}
	}

	public EsQueryIndexBean getIndexConfig() {
		return indexConfig;
	}

	public void setIndexConfig(EsQueryIndexBean indexConfig) {
		this.indexConfig = indexConfig;
	}
}
