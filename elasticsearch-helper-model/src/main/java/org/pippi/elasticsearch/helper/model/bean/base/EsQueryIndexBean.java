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


	private String clientKey;

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

	public HighLightBean getHighLight() {
		return highLight;
	}

	public void setHighLight(HighLightBean highLight) {
		this.highLight = highLight;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public QueryModel getEsQueryModel() {
		return esQueryModel;
	}

	public void setEsQueryModel(QueryModel esQueryModel) {
		this.esQueryModel = esQueryModel;
	}

	public String[] getFetchFields() {
		return fetchFields;
	}

	public void setFetchFields(String[] fetchFields) {
		this.fetchFields = fetchFields;
	}

	public String[] getExcludeFields() {
		return excludeFields;
	}

	public void setExcludeFields(String[] excludeFields) {
		this.excludeFields = excludeFields;
	}

	public float getMinScore() {
		return minScore;
	}

	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}

	public boolean getTraceScore() {
		return traceScore;
	}

	public void setTraceScore(boolean traceScore) {
		this.traceScore = traceScore;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public FuncScoreBean getFuncScoreBean() {
		return funcScoreBean;
	}

	public void setFuncScoreBean(FuncScoreBean funcScoreBean) {
		this.funcScoreBean = funcScoreBean;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
}
