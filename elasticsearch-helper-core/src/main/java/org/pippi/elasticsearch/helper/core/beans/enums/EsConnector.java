package org.pippi.elasticsearch.helper.core.beans.enums;

/**
 * define elasticsearch's query-logic-connector
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public enum EsConnector {

	/**
	 * influence doc's recall and doc's score
	 * 影响文档召回及文档评分
	 */
	MUST,
	/**
	 * influence doc's recall and doc's score
	 * 影响文档召回及文档评分
	 */
	MUST_NOT,
	/**
	 * influence doc's recall, but un-influence doc's score
	 * 只影响文档召回，对召回文档进行过滤，不会影响文档评分
	 */
	FILTER,
	/**
	 * un-influence the results-hit, just influence doc's score
	 * 不影响文档召回，只影响文档评分
	 */
	SHOULD

}
