package org.pippi.elasticsearch.helper.core.beans.mapping;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryField;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;

import java.io.Serializable;

/**
 * mapping the annotation information of @EsQueryFiled
 * @see EsQueryField
 * @author JohenTeng
 * @date 2021/8/9
 */
public class EsQueryFieldBean<T> implements Serializable {

	/**
	 *  对应查询字段
	 */
	private String column;

	/**
	 *  查询参数值
	 */
	private Object value;

	/**
	 *  多个查询参数值
	 */
	private Object[] values;

	/**
	 *  查询类型 (required)
	 */
	private String queryType;

	/**
	 *  逻辑连接符 (required)
	 */
	private EsConnector logicConnector;

	/**
	 *  对应字段类型 (required)
	 */
	private String meta;

	/**
	 *  查询脚本
	 */
	private String script;

	/**
	 *  权重
	 */
	private Float boost;

	/**
	 *  自定义扩展信息
	 */
	private String extendDefine;
	/**
	 *  自定义扩展信息映射对象
	 */
	private T extendBean;

	public EsQueryFieldBean() {
	}

	public EsQueryFieldBean(String column,
		String queryType,
		EsConnector logicConnector,
		String meta,
		String script) {
		this.column = column;
		this.queryType = queryType;
		this.logicConnector = logicConnector;
		this.meta = meta;
		this.script = script;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
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

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getExtendDefine() {
		return extendDefine;
	}

	public void setExtendDefine(String extendDefine) {
		this.extendDefine = extendDefine;
	}

	public T getExtendBean() {
		return extendBean;
	}

	public void setExtendBean(T extendBean) {
		this.extendBean = extendBean;
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
}
