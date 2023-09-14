package org.pippi.elasticsearch.helper.lambda.utils;

import org.pippi.elasticsearch.helper.lambda.utils.support.LambdaMeta;
import org.pippi.elasticsearch.helper.lambda.utils.support.ProxyLambdaMeta;
import org.pippi.elasticsearch.helper.lambda.utils.support.EsFunction;
import org.pippi.elasticsearch.helper.lambda.utils.support.SerializedLambdaMeta;
import org.pippi.elasticsearch.helper.model.exception.EsHelperException;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JohenDeng
 * @date 2023/9/11
 * copy from mybatis-plus
 **/
public class LambdaUtils {

    /**
     * 该缓存可能会在任意不定的时间被清除
     *
     * @param func 需要解析的 lambda 对象
     * @param <T>  类型，被调用的 Function 对象的目标类型
     * @return 返回解析后的结果
     */
    public static <T> LambdaMeta extract(EsFunction<T, ?> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            return new SerializedLambdaMeta((SerializedLambda) ReflectionUtils.setAccessible(method).invoke(func));
        } catch (NoSuchMethodException e) {
            if (func instanceof Proxy) return new ProxyLambdaMeta((Proxy) func);
            String message = "Cannot find method writeReplace, please make sure that the lambda composite class is currently passed in";
            throw new EsHelperException(message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new EsHelperException(e);
        }
    }

}
