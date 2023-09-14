package org.pippi.elasticsearch.helper.lambda.wrapper;

import org.pippi.elasticsearch.helper.lambda.wrapper.query.EsLambdaQueryWrapper;
import org.pippi.elasticsearch.helper.lambda.wrapper.query.EsQueryWrapper;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
public class Wrappers {

    public static <T>EsLambdaQueryWrapper<T> lambdaQuery() {
        return new EsLambdaQueryWrapper<>();
    }

    public static <T>EsLambdaQueryWrapper<T> lambdaQuery(Class<T> clazz) {
        return new EsLambdaQueryWrapper<>(clazz);
    }

    public static <T extends EsEntity> EsQueryWrapper<T> query() {
        return new EsQueryWrapper<>();
    }

    public static <T extends EsEntity> EsQueryWrapper<T> query(Class<T> clazz) {
        return new EsQueryWrapper<>(clazz);
    }
}
