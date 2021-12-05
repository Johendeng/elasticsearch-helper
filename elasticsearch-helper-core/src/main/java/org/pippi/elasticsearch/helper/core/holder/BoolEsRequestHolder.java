package org.pippi.elasticsearch.helper.core.holder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

/**
 * bool query builder holder
 *
 * author     JohenTeng
 * date      2021/8/9
 */
public class BoolEsRequestHolder extends AbstractEsRequestHolder<BoolQueryBuilder> {

	/**
	 *  默认使用must连接
	 */
	@Override
	protected void defineDefaultLogicConnector() {
		super.setCurrentQueryBuilderList(super.getQueryBuilder().must());
	}

	@Override
	protected void defineQueryBuilder() {
		super.setQueryBuilder(QueryBuilders.boolQuery());
	}

	@Override
	public AbstractEsRequestHolder changeLogicConnector(EsConnector logicKey) {
		if (logicKey == null) {
			return this;
		}
		if (EsConnector.MUST.equals(logicKey)) {
			super.setCurrentQueryBuilderList(super.getQueryBuilder().must());
		}
		if (EsConnector.FILTER.equals(logicKey)) {
			super.setCurrentQueryBuilderList(super.getQueryBuilder().filter());
		}
		if (EsConnector.MUST_NOT.equals(logicKey)) {
			super.setCurrentQueryBuilderList(super.getQueryBuilder().mustNot());
		}
		if (EsConnector.SHOULD.equals(logicKey)) {
			super.setCurrentQueryBuilderList(super.getQueryBuilder().should());
		}
		return this;
	}


}
