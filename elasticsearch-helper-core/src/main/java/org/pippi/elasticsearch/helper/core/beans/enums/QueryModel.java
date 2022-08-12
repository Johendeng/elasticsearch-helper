package org.pippi.elasticsearch.helper.core.beans.enums;

/**
 * es-helper support query model
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public enum QueryModel {

	/**
	 * 组合匹配文档查询
	 * bool
	 * @see org.elasticsearch.index.query.BoolQueryBuilder
	 */
	BOOL,

	/**
	 * 最匹配文档查询，类似查询multi_match
	 * dis_max
	 * @see org.elasticsearch.index.query.DisMaxQueryBuilder
	 */
	DIS_MAX,

	/**
	 * 文档自定义重评分
	 * @see org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
	 */
	FUNC_SCORE,
	;
}
