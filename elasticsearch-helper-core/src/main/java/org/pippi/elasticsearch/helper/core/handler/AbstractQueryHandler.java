package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.ScoreFuncBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.core.session.FuncScoreEsSession;
import org.pippi.elasticsearch.helper.model.utils.ExtAnnBeanMapUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @date     2021/5/3
 * @author    JohenTeng
 *
 * @param <T> mapping JavaBean
 **/
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractQueryHandler<T extends QueryConf> {

    AbstractQueryHandler(){}

    /**
     *  execute param-explain
     * return
     */
    public final AbstractEsSession execute(EsQueryFieldBean<T> queryDes, AbstractEsSession session){
        session.changeLogicConnector(queryDes.getLogicConnector());
        handleExtBean(queryDes);
        QueryBuilder queryBuilder = handle(queryDes, session);
        if (Objects.nonNull(queryBuilder)) {
            queryBuilder.boost(queryDes.getBoost());
            session.chain(queryBuilder);
            queryDes.getExtBean().configQueryBuilder(queryBuilder);
            this.handleExtConfig(queryDes, queryBuilder);
        }
        this.handleFuncScoreQuery(queryDes, session);
        return session;
    }

    /**
     *  abstract Handle method, you need to implement it
     * @param queryDes
     * @param searchHelper
     * return
     */
    public abstract QueryBuilder handle (EsQueryFieldBean<T> queryDes, AbstractEsSession searchHelper);


    /**
     *  explain the extend-params
     *  final handle-process of query-field-reader
     * @param queryDes
     * return
     */
    protected void handleExtConfig (EsQueryFieldBean<T> queryDes, QueryBuilder queryBuilder) {
        // do nothing, if need translate QueryDes.extendDefine, you need implement this method
    }

    /**
     *  mapping the annotation
     * @param queryDes
     * return
     */
    protected final EsQueryFieldBean<T> handleExtBean(EsQueryFieldBean<T> queryDes) {
        if (queryDes.getExtBean() != null) {
            return queryDes;
        }
        try {
            if (!(this.getClass().getGenericSuperclass() instanceof ParameterizedType)) {
                return queryDes;
            }
            Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
            if (actualTypeArguments.length == 0) {
                return queryDes;
            }
            String fullClassPath = actualTypeArguments[0].getTypeName();
            Class<?> aClass = Class.forName(fullClassPath);
            T extBean = (T) ExtAnnBeanMapUtils.mapping(queryDes.getExtAnnotation(), aClass);
            queryDes.setExtBean(extBean);
            return queryDes;
        } catch (ClassNotFoundException e) {
            throw new EsHelperConfigException("queryHandle-actualType class not found, cause:", e);
        }
    }

    protected final void handleFuncScoreQuery(EsQueryFieldBean<T> queryDes, AbstractEsSession searchHelper) {
        if (!(searchHelper instanceof FuncScoreEsSession)) {
            return;
        }
        FuncScoreEsSession funcScoreEsHolder = (FuncScoreEsSession) searchHelper;
        Annotation funcScoreAnn = queryDes.getFuncScoreAnn();
        if (Objects.isNull(funcScoreAnn)) {
            return;
        }
        ScoreFunctionBuilder scoreFuncBuilder = ScoreFuncBuilder.generate(queryDes);
        funcScoreEsHolder.addFilterFunc(scoreFuncBuilder);
    }
}
