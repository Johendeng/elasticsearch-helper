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
	private String field;

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
	 *  评分底分
	 */
	private Float boost;

	/**
	 *  自定义扩展信息映射对象
	 */
	private T extendBean;

	/**
	 *  自定义扩展信息
	 */
	private String detail;

	/**
	 *  定义字段是否需要高亮
	 */
	private HighLight highLight;

	public EsQueryFieldBean() {
	}

	public EsQueryFieldBean(String field,
							String queryType,
							EsConnector logicConnector,
							String meta,
							String script){
		this.field = field;
		this.queryType = queryType;
		this.logicConnector = logicConnector;
		this.meta = meta;
		this.script = script;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setHighLight(boolean enable, String key) {
		this.highLight = new HighLight(enable, key);
	}

	public HighLight getHighLight() {
		return highLight;
	}

	public static class HighLight {

		private boolean enable;
		private String key;

		public HighLight(boolean enable, String key) {
			this.enable = enable;
			this.key = key;
		}

		public boolean isEnable() {
			return enable;
		}

		public void setEnable(boolean enable) {
			this.enable = enable;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}

}
