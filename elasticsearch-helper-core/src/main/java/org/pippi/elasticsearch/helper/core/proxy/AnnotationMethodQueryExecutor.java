package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.hook.EsHookReedits;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.pippi.elasticsearch.helper.core.reader.EsResponseReader;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.lambda.mapper.EsBaseMapper;
import org.pippi.elasticsearch.helper.model.annotations.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.model.annotations.hook.UseResponseHook;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
public class AnnotationMethodQueryExecutor extends EsOperationExecutor{

    private static final AnnotationMethodQueryExecutor INSTANCE = new AnnotationMethodQueryExecutor();

    private static final Logger log = LoggerFactory.getLogger("ANNOTATION-METHOD-QUERY-EXECUTOR");

    private AnnotationMethodQueryExecutor() {}

    public static EsOperationExecutor executor() {
        return INSTANCE;
    }

    @Override
    boolean condition(Class<?> targetInterface, Method method, Object[] args) {
        if (method.getDeclaringClass().equals(EsBaseMapper.class)) {
            return false;
        }
        if (args != null && method.isAnnotationPresent(EsAnnQueryIndex.class)) {
            boolean flag = Arrays.stream(args).anyMatch(arg -> arg.getClass().isAnnotationPresent(EsAnnQueryIndex.class));
            if (flag) {
                throw new RuntimeException("two types query-define dont exist as the same time");
            }
            Annotation[][] paramAnnArr = method.getParameterAnnotations();
            if (paramAnnArr.length != args.length) {
                throw new RuntimeException("each param should annotation by (@Term, @Terms ...) any one");
            }
            return true;
        }
        return false;
    }

    @Override
    Object operate(RestHighLevelClient client,
                   Class<?> targetInterface,
                   RequestOptions requestOption,
                   Method method,
                   Object[] args,
                   boolean visitParent,
                   boolean statementLogOut) {
        AbstractEsSession<?> session = EsQueryEngine.execute(method, args);
        session = this.handleRequestHook(session, method, args);
        SearchResponse resp = null;
        try {
            SearchRequest request = session.getRequest();
            if (statementLogOut) {
                log.info("{} # {} execute-es-query-json is\n{}", targetInterface.getSimpleName(), method.getName(), session.getSource().toString());
            }
            resp = client.search(request, requestOption);
        } catch (IOException e) {
            throw new EsHelperQueryException("EsSearchExecute I/O exception, cause:", e);
        }
        return handleRespHookIfPresent(method, resp);
    }

    private RequestHook<Object[]> checkRequestHook (Method method) {
        if (method.isAnnotationPresent(UseRequestHook.class)) {
            UseRequestHook useReqHookAnn = method.getAnnotation(UseRequestHook.class);
            String reqHookKey = useReqHookAnn.value();
            try {
                return (RequestHook<Object[]>) EsHookReedits.REP_FUNC_REGEDIT.get(reqHookKey);
            } catch (Exception e) {
                throw new EsHelperConfigException("METHOD-ANN-QUERY REQUEST-HOOK HAVE TO DEFINE PARAM-TYPE AS Object[]", e);
            }
        }
        return null;
    }

    private AbstractEsSession<?> handleRequestHook(AbstractEsSession<?> session, Method method, Object[] args) {
        RequestHook<Object[]> requestHook = null;
        if ((requestHook = checkRequestHook(method)) != null) {
            session = requestHook.handleRequest(session, args);
        }
        return session;
    }

    private ResponseHook checkResponseHook(Method method) {
        if (method.isAnnotationPresent(UseResponseHook.class)) {
            UseResponseHook useResponseHookAnn = method.getAnnotation(UseResponseHook.class);
            String responseHookKey = useResponseHookAnn.value();
            return EsHookReedits.RESP_FUNC_REGEDIT.get(responseHookKey);
        }
        return null;
    }

    private Object handleRespHookIfPresent(Method method, SearchResponse resp) {
        ResponseHook responseHook = null;
        if ((responseHook = checkResponseHook(method)) != null) {
            return responseHook.handleResponse(resp);
        } else {
            return EsResponseReader.readResp(method, resp);
        }
    }
}
