package org.pippi.elasticsearch.helper.core.elasticsearch.enums;

/**
 * 描述 ES 涉及的查询逻辑连接符
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/9
 */
public enum LogicConnector {

	MUST,
	MUST_NOT,
	FILTER,
	/**
	 *  不会影响过滤， 只影响文档评分
	 */
	SHOULD

}
