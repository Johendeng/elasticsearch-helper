package org.pippi.elasticsearch.helper.lambda.wrapper;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;

import java.util.List;

/**
 * 仅支持 bool 查询
 *
 * @author JohenDeng
 * @date 2023/8/28
 **/
public abstract class EsWrapper<T extends EsEntity> {

    public abstract T getEntity();

    protected EsConnector currentConnector;

    protected QueryBuilder queryBuilder = QueryBuilders.boolQuery();

    protected List<EsQueryFieldBean<?>> queryDesList = Lists.newArrayList();

    protected void connector(EsConnector currentConnector) {
        this.currentConnector = currentConnector;
    }

}
