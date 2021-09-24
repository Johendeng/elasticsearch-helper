package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping;


import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/8/9
 */
public class EsQueryIndexBean {

	private String indexName;

	private QueryModel esQueryModel;

	private String[] fetchFields;

	private String[] excludeFields;

	public EsQueryIndexBean() {
	}

	public EsQueryIndexBean(String indexName, QueryModel esQueryModel, String[] fetchFields, String[] excludeFields) {
		this.indexName = indexName;
		this.esQueryModel = esQueryModel;
		this.fetchFields = fetchFields;
		this.excludeFields = excludeFields;
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
}
