package org.pippi.elasticsearch.helper.lambda.utils.support;

import org.pippi.elasticsearch.helper.model.exception.EsHelperException;
import org.pippi.elasticsearch.helper.model.utils.ClassUtils;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;

/**
 * @author JohenDeng
 * @date 2023/9/11
 **/
public class SerializedLambdaMeta implements LambdaMeta{


    private static final Field FIELD_CAPTURING_CLASS;

    static {
        try {
            Class<SerializedLambda> aClass = SerializedLambda.class;
            FIELD_CAPTURING_CLASS = ReflectionUtils.setAccessible(aClass.getDeclaredField("capturingClass"));
        } catch (NoSuchFieldException e) {
            throw new EsHelperException(e);
        }
    }

    private final SerializedLambda lambda;

    public SerializedLambdaMeta(SerializedLambda lambda) {
        this.lambda = lambda;
    }

    @Override
    public String getImplMethodName() {
        return lambda.getImplMethodName();
    }

    @Override
    public Class<?> getInstantiatedClass() {
        String instantiatedMethodType = lambda.getInstantiatedMethodType();
        String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(';')).replace('/', '.');
        return ClassUtils.toClassConfident(instantiatedType, getCapturingClass().getClassLoader());
    }

    public Class<?> getCapturingClass() {
        try {
            return (Class<?>) FIELD_CAPTURING_CLASS.get(lambda);
        } catch (IllegalAccessException e) {
            throw new EsHelperException(e);
        }
    }
}
