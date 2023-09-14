package org.pippi.elasticsearch.helper.lambda.utils.support;

/**
 * @author JohenDeng
 * @date 2023/9/11
 * copy from mybatis-plus
 **/
public interface LambdaMeta {

    /**
     * 获取 lambda 表达式实现方法的名称
     *
     * @return lambda 表达式对应的实现方法名称
     */
    String getImplMethodName();

    /**
     * 实例化该方法的类
     *
     * @return 返回对应的类名称
     */
    Class<?> getInstantiatedClass();
}
