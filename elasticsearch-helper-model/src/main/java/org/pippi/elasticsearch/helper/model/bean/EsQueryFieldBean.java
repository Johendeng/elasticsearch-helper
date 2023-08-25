package org.pippi.elasticsearch.helper.model.bean;

import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.*;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;


import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * mapping the annotation information of
 * annotated by {@link Query}
 *
 * ex:
 * 	{@link Match}
 * 	{@link Fuzzy}
 * 	{@link Term}
 * 	{@link Terms}
 * 	{@link Nested}
 * 	{@link MultiMatch}
 *    ......
 *
 * @see org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base
 * @author     JohenTeng
 * @date      2021/8/9
 */
public class EsQueryFieldBean<T extends QueryBean> implements Serializable {

	/**
	 *  the field be queried
	 */
	private String field;

	/**
	 *  the val of query for
	 */
	private Object value;

	/**
	 *  query Type like <h>Match,Fuzzy,Term ...</h>
	 */
	private String queryType;

	/**
	 *  connector of multi-query (must, should, must not, filter)
	 */
	private EsConnector logicConnector;

	/**
	 *  meta of field
	 */
	private String meta;

	/**
	 *  boost score default 1.0
	 */
	private Float boost;

	/**
	 *  extend query bean
	 */
	private T extBean;

	/**
	 * annotation which annotated by @Query
	 */
	private Annotation extAnnotation;

	/**
	 * annotation which annotated by @FuncQuery
	 * 	it's function_score method
	 */
	private Annotation funcScoreAnn;


	/**
	 *  high light enable ?
	 */
	private boolean highLight;


	private String highLightKey;

	public EsQueryFieldBean() {
	}

	public EsQueryFieldBean(String field,
							String queryType,
							EsConnector logicConnector,
							String meta){
		this.field = field;
		this.queryType = queryType;
		this.logicConnector = logicConnector;
		this.meta = meta;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public T getExtBean() {
		return extBean;
	}

	public void setExtBean(T extBean) {
		this.extBean = extBean;
	}

	public Annotation getExtAnnotation() {
		return extAnnotation;
	}

	public void setExtAnnotation(Annotation extAnnotation) {
		this.extAnnotation = extAnnotation;
	}

	public Float getBoost() {
		return boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
	}

	public EsConnector getLogicConnector() {
		return logicConnector;
	}

	public void setLogicConnector(EsConnector logicConnector) {
		this.logicConnector = logicConnector;
	}

	public boolean isHighLight() {
		return highLight;
	}

	public void setHighLight(boolean highLight) {
		this.highLight = highLight;
	}

	public String getHighLightKey() {
		return highLightKey;
	}

	public void setHighLightKey(String highLightKey) {
		this.highLightKey = highLightKey;
	}

	public Annotation getFuncScoreAnn() {
		return funcScoreAnn;
	}

	public void setFuncScoreAnn(Annotation funcScoreAnn) {
		this.funcScoreAnn = funcScoreAnn;
	}
}
