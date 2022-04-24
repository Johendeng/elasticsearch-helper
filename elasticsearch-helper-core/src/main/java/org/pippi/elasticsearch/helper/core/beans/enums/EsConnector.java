package org.pippi.elasticsearch.helper.core.beans.enums;

/**
 * define elasticsearch's query-logic-connector
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public enum EsConnector {

	/**
	 *  influence doc's recall and doc's score
	 */
	MUST,
	/**
	 * influence doc's recall and doc's score
	 */
	MUST_NOT,
	/**
	 * influence doc's recall, but un-influence doc's score
	 */
	FILTER,
	/**
	 *  un-influence the results-hit, just influence doc's score
	 *
	 */
	SHOULD

}
