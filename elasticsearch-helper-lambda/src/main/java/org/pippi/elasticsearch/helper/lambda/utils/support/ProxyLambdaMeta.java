package org.pippi.elasticsearch.helper.lambda.utils.support;

import org.pippi.elasticsearch.helper.model.exception.EsHelperException;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author JohenDeng
 * @date 2023/9/11
 * copy from mybatis-plus
 **/
public class ProxyLambdaMeta implements LambdaMeta{


    private static final Field FIELD_MEMBER_NAME;
    private static final Field FIELD_MEMBER_NAME_CLAZZ;
    private static final Field FIELD_MEMBER_NAME_NAME;

    static {
        try {
            Class<?> classDirectMethodHandle = Class.forName("java.lang.invoke.DirectMethodHandle");
            FIELD_MEMBER_NAME = ReflectionUtils.setAccessible(classDirectMethodHandle.getDeclaredField("member"));
            Class<?> classMemberName = Class.forName("java.lang.invoke.MemberName");
            FIELD_MEMBER_NAME_CLAZZ = ReflectionUtils.setAccessible(classMemberName.getDeclaredField("clazz"));
            FIELD_MEMBER_NAME_NAME = ReflectionUtils.setAccessible(classMemberName.getDeclaredField("name"));
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new EsHelperException(e);
        }
    }

    private final Class<?> clazz;
    private final String name;

    public ProxyLambdaMeta(Proxy func) {
        InvocationHandler handler = Proxy.getInvocationHandler(func);
        try {
            Object dmh = ReflectionUtils.setAccessible(handler.getClass().getDeclaredField("val$target")).get(handler);
            Object member = FIELD_MEMBER_NAME.get(dmh);
            clazz = (Class<?>) FIELD_MEMBER_NAME_CLAZZ.get(member);
            name = (String) FIELD_MEMBER_NAME_NAME.get(member);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new EsHelperException(e);
        }
    }

    @Override
    public String getImplMethodName() {
        return name;
    }

    @Override
    public Class<?> getInstantiatedClass() {
        return clazz;
    }

    @Override
    public String toString() {
        return clazz.getSimpleName() + "::" + name;
    }
}
