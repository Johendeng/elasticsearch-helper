//package org.pippi.elasticsearch.helper.core.elasticsearch.engine.handler;
//
//import org.pippi.elasticsearch.helper.core.elasticsearch.engine.EsQueryEngine;
//import org.pippi.elasticsearch.helper.core.elasticsearch.engine.beans.EsQueryFieldBean;
//import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
////import org.springframework.beans.factory.InitializingBean;
//
///**
// * date:    2021/5/3
// * @author: dengtianjia@fiture.com
// **/
//public abstract class AbstractQueryHandle {//implements InitializingBean {
//
//    AbstractQueryHandle(){}
//
//    /**
//     *  定义查询类型
//     * @return
//     */
//    public abstract String getQueryType();
//
//    /**
//     *  执行方法
//     * @param queryDes
//     * @param searchHelper
//     * @return
//     */
//    public AbstractEsRequestHolder execute(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper){
//        EsQueryFieldBean handledDes = explainExtendDefine(queryDes);
//        return handle(handledDes, searchHelper);
//    }
//
//    /**
//     *
//     * @param queryDes
//     * @return
//     */
//    public EsQueryFieldBean explainExtendDefine (EsQueryFieldBean queryDes) {
//        // do nothing, if need translate EsQuery.extendDefine, you need implement this method
//        return queryDes;
//    }
//
//    /**
//     *
//     * 通过 查询描述类构建单个查询对象
//     * @param queryDes
//     * @param searchHelper
//     * @return
//     */
//    public abstract AbstractEsRequestHolder handle (EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper);
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        EsQueryEngine.addHandle(this.getQueryType(), this);
//    }
//}
