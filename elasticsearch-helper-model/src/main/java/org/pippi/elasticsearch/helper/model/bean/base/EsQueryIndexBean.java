package org.pippi.elasticsearch.helper.model.bean.base;


import org.pippi.elasticsearch.helper.model.enums.QueryModel;

/**
 * ElasticSearch Index structure mapping bean
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public class EsQueryIndexBean {

	private String indexName;

	private QueryModel esQueryModel = QueryModel.BOOL;

	private String[] fetchFields;

	private String[] excludeFields;

	private float minScore = 0.0f;

	private boolean traceScore = true;

	private HighLightBean highLight;

	private int size = 10;

	private FuncScoreBean funcScoreBean;


	private String clientKey = "primary";

	public EsQueryIndexBean() {
	}

	public EsQueryIndexBean(String indexName, QueryModel esQueryModel) {
		this.indexName = indexName;
		this.esQueryModel = esQueryModel;
	}

	public EsQueryIndexBean(String indexName, QueryModel esQueryModel, String clientKey) {
		this.indexName = indexName;
		this.esQueryModel = esQueryModel;
		this.clientKey = clientKey;
	}

	public EsQueryIndexBean(String indexName,
							QueryModel esQueryModel,
							String[] fetchFields,
							String[] excludeFields,
							float minScore,
							boolean traceScore) {
		this.indexName = indexName;
		this.esQueryModel = esQueryModel;
		this.fetchFields = fetchFields;
		this.excludeFields = excludeFields;
		this.minScore = minScore;
		this.traceScore = traceScore;
	}

	public EsQueryIndexBean setIndexName(String indexName) {
		this.indexName = indexName;
		return this;
	}

	public EsQueryIndexBean setEsQueryModel(QueryModel esQueryModel) {
		this.esQueryModel = esQueryModel;
		return this;
	}

	public EsQueryIndexBean setFetchFields(String[] fetchFields) {
		this.fetchFields = fetchFields;
		return this;
	}

	public EsQueryIndexBean setExcludeFields(String[] excludeFields) {
		this.excludeFields = excludeFields;
		return this;
	}

	public EsQueryIndexBean setMinScore(float minScore) {
		this.minScore = minScore;
		return this;
	}

	public EsQueryIndexBean setTraceScore(boolean traceScore) {
		this.traceScore = traceScore;
		return this;
	}

	public EsQueryIndexBean setHighLight(HighLightBean highLight) {
		this.highLight = highLight;
		return this;
	}

	public EsQueryIndexBean setSize(int size) {
		this.size = size;
		return this;
	}

	public EsQueryIndexBean setFuncScoreBean(FuncScoreBean funcScoreBean) {
		this.funcScoreBean = funcScoreBean;
		return this;
	}

	public EsQueryIndexBean setClientKey(String clientKey) {
		this.clientKey = clientKey;
		return this;
	}

	public HighLightBean getHighLight() {
		return highLight;
	}

	public String getIndexName() {
		return indexName;
	}

	public QueryModel getEsQueryModel() {
		return esQueryModel;
	}

	public String[] getFetchFields() {
		return fetchFields;
	}

	public String[] getExcludeFields() {
		return excludeFields;
	}

	public float getMinScore() {
		return minScore;
	}

	public boolean getTraceScore() {
		return traceScore;
	}

	public int getSize() {
		return size;
	}

	public FuncScoreBean getFuncScoreBean() {
		return funcScoreBean;
	}

	public String getClientKey() {
		return clientKey;
	}
}
