package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping;


import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

/**
 * ElasticSearch Index structure mapping bean
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public class EsQueryIndexBean {

	private String indexName;

	private QueryModel esQueryModel;

	private String[] fetchFields;

	private String[] excludeFields;

	private float minScore;

	private boolean traceScore;

	private HighLightBean highLight;

	public EsQueryIndexBean() {
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
}
