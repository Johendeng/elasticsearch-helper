package org.pippi.elasticsearch.helper.core.beans.enums;

/**
 * 描述 ES 涉及的查询逻辑连接符
 * define elasticsearch's query-logic-connector
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public enum EsConnector {

	/**
	 *  ---- bool query
	 */
	MUST,
	MUST_NOT,
	FILTER,
	/**
	 *  不会影响过滤， 只影响文档评分
	 *  un-influence the results-hit, just influence doc's score
	 *
	 */
	SHOULD

}
