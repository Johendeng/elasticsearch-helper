//package org.pippi.elasticsearch.helper.core.elasticsearch.engine.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.stereotype.Component;
//
///**
// * 描述
// *
// * @author dengtianjia@fiture.com
// * @date 2021/8/9
// */
//@Slf4j
//@Component
//public class TermsQueryHandle extends AbstractQueryHandle{
//
//	@Override
//	public String getQueryType() {
//		return QueryType.TERM.getQuery();
//	}
//
//	@Override
//	public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {
//		searchHelper.changeLogicConnector(queryDes.getLogicConnector());
//		if (queryDes.getValue() != null) {
//			searchHelper.chain(QueryBuilders.termQuery(queryDes.getColumn(), queryDes.getValue()));
//		}
//		if (queryDes.getValues() != null) {
//			searchHelper.chain(QueryBuilders.termsQuery(queryDes.getColumn(), queryDes.getValues()));
//		}
//		return searchHelper;
//	}
//
//}
