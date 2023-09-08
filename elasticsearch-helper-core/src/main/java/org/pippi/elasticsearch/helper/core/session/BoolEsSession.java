package org.pippi.elasticsearch.helper.core.session;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;

/**
 * bool query builder holder
 *
 * @author     JohenTeng
 * @date      2021/8/9
 */
@SuppressWarnings("all")
public class BoolEsSession extends AbstractEsSession<BoolQueryBuilder> {

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
	public AbstractEsSession changeLogicConnector(EsConnector logicKey) {
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

	public static BoolEsSession buildSimpleSession() {
		BoolEsSession session = new BoolEsSession();
		session.setQueryBuilder(QueryBuilders.boolQuery());
		session.defineQueryBuilder();
		session.defineDefaultLogicConnector();
		return session;
	}
}
