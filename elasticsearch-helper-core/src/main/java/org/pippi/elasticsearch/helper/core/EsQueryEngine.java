package org.pippi.elasticsearch.helper.core;

import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author    JohenTeng
 * @date     2021/7/17
 **/
@SuppressWarnings("all")
public class EsQueryEngine {

    /**
     * @param queryViewObj
     * @param visitParent
     * return
     */
    public static AbstractEsSession execute(Object queryViewObj, boolean visitParent) {
        QueryAnnParser translator = QueryAnnParser.instance();
        EsQueryIndexBean indexDefineBean = translator.getIndex(queryViewObj);
        List<EsQueryFieldBean> queryDesList = translator.read(queryViewObj, visitParent);
        AbstractEsSession session = AbstractEsSession.builder().config(indexDefineBean).build();
        loadEsExcDesBean(session, queryDesList);
        return session;
    }

    public static AbstractEsSession<?> execute(Method method, Object[] args) {
        QueryAnnParser translator = QueryAnnParser.instance();
        EsQueryIndexBean indexDefineBean = translator.getIndex(method);
        AbstractEsSession session = AbstractEsSession.builder().config(indexDefineBean).build();
        Parameter[] params = method.getParameters();
        Annotation[][] annArr = method.getParameterAnnotations();
        List<EsQueryFieldBean> queryDesList = translator.read(params, args, annArr);
        loadEsExcDesBean(session, queryDesList);
        return session;
    }


    private static void loadEsExcDesBean(AbstractEsSession session, List<EsQueryFieldBean> queryDesList) {
        for (EsQueryFieldBean queryDes : queryDesList) {
            String queryKey = queryDes.getQueryType();
            AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
            queryHandle.execute(queryDes, session);
        }
    }
}
