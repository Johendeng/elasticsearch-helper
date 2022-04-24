package org.pippi.elasticsearch.helper.core.beans.enums;

/**
 * es-helper support query model
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
public enum QueryModel {

	/**
	 * bool
	 * @see org.elasticsearch.index.query.BoolQueryBuilder
	 */
	BOOL,
	/**
	 * dis_max
	 * @see org.elasticsearch.index.query.DisMaxQueryBuilder
	 */
	DIS_MAX,
}
