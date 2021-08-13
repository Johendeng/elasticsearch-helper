//package org.pippi.elasticsearch.helper.core.elasticsearch.engine.holder;
//
//import com.fiture.content.search.common.elasticsearch.enums.LogicConnector;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//
///**
// * 描述 es组合查询 请求持有对象
// *
// * @author dengtianjia@fiture.com
// * @date 2021/8/9
// */
//public class BoolEsRequestHolder extends AbstractEsRequestHolder<BoolQueryBuilder> {
//
//	/**
//	 *  默认使用must连接
//	 */
//	@Override
//	protected void defineDefaultLogicConnector() {
//		super.setCurrentQueryBuilderList(super.getQueryBuilder().must());
//	}
//
//	@Override
//	protected void defineQueryBuilder() {
//		super.setQueryBuilder(QueryBuilders.boolQuery());
//	}
//
//	@Override
//	public AbstractEsRequestHolder changeLogicConnector(LogicConnector logicKey) {
//		if (logicKey == null) {
//			return this;
//		}
//		if (LogicConnector.MUST.equals(logicKey)) {
//			super.setCurrentQueryBuilderList(super.getQueryBuilder().must());
//		}
//		if (LogicConnector.FILTER.equals(logicKey)) {
//			super.setCurrentQueryBuilderList(super.getQueryBuilder().filter());
//		}
//		if (LogicConnector.MUST_NOT.equals(logicKey)) {
//			super.setCurrentQueryBuilderList(super.getQueryBuilder().mustNot());
//		}
//		if (LogicConnector.SHOULD.equals(logicKey)) {
//			super.setCurrentQueryBuilderList(super.getQueryBuilder().should());
//		}
//		return this;
//	}
//
//
//}
