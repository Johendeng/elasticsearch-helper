package org.pippi.elasticsearch.helper.core.proxy;

import com.google.common.collect.Sets;
import org.elasticsearch.client.RequestOptions;
import org.pippi.elasticsearch.helper.core.EsRestClientFactory;
import org.pippi.elasticsearch.helper.core.QueryAnnParser;
import org.pippi.elasticsearch.helper.core.wrapper.impl.EsBaseMapperImpl;
import org.pippi.elasticsearch.helper.core.wrapper.mapper.EsBaseMapper;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
@SuppressWarnings({"rawtypes", "unchecked"})
public class LambdaQueryExecutor extends EsOperationExecutor {

    private static final Logger log = LoggerFactory.getLogger("LAMBDA-QUERY-EXECUTOR");

    private static final LambdaQueryExecutor INSTANCE = new LambdaQueryExecutor();

    private LambdaQueryExecutor() {}

    public static EsOperationExecutor executor() {
        return INSTANCE;
    }

    private final Set<Method> BASE_MAPPER_METHOD_SET = Sets.newHashSet(EsBaseMapper.class.getDeclaredMethods());

    @Override
    boolean condition(Class<?> targetInterface, Method method, Object[] args) {
        return BASE_MAPPER_METHOD_SET.contains(method);
    }

    @Override
    Object operate(Class<?> targetInterface, RequestOptions requestOption, Method method, Object[] args, boolean visitParent, boolean statementLogOut) {
        ParameterizedType mapperType = ReflectionUtils.findTargetSuperParameterizedInterface(targetInterface, EsBaseMapper.class);
        Type[] acturalType = mapperType.getActualTypeArguments();
        Class<?> entityClazz = (Class<?>) acturalType[0];
        EsIndex indexAnn = entityClazz.getAnnotation(EsIndex.class);
        EsQueryIndexBean indexBean = QueryAnnParser.instance().parseIndexAnn(entityClazz.getSimpleName(), indexAnn);
        EsBaseMapper<?> mapper = new EsBaseMapperImpl(indexBean.getIndexName(), entityClazz,
                EsRestClientFactory.getClient(indexBean.getClientKey()), requestOption, statementLogOut, log);
        return ReflectionUtils.methodInvoke(mapper, method, args);
    }
}
