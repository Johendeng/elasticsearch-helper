package org.pippi.elasticsearch.helper.beans.mapping;


import org.pippi.elasticsearch.helper.beans.enums.QueryModel;

/**
 * 描述
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/9
 */
public class EsQueryIndexBean {

	private String indexName;

	private QueryModel esQueryModel;

	public EsQueryIndexBean() {
	}

	public EsQueryIndexBean(String indexName, QueryModel esQueryModel) {
		this.indexName = indexName;
		this.esQueryModel = esQueryModel;
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
}
