package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.EsPage;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.PageAndOrder;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * PageAndOrderQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
@EsQueryHandle(PageAndOrder.class)
public class PageAndOrderQueryHandler extends AbstractQueryHandler{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (!value.getClass().equals(EsPage.class)) {
            throw new EsHelperQueryException("@PageAndOrder have to define as PageParam.class");
        }
        EsPage esPage = (EsPage) value;
        SearchSourceBuilder source = searchHelper.getSource();
        source.from(esPage.getExclude()).size(esPage.getPageSize());
        LinkedHashMap<String, SortOrder> orderMap = esPage.getOrderMap();
        if (MapUtils.isNotEmpty(orderMap)) {
            for (Map.Entry<String, SortOrder> entry : orderMap.entrySet()) {
                source.sort(entry.getKey(), Objects.nonNull(entry.getValue()) ? entry.getValue() : SortOrder.ASC);
            }
        }
        return null;
    }
}
