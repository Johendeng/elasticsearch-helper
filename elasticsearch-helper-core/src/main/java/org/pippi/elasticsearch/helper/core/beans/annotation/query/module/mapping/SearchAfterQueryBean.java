package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * SearchAfterQueryBean
 *
 * @author JohenTeng
 * @date 2022/4/28
 */
public class SearchAfterQueryBean extends QueryBean{

    private SortOrder order;

    private Integer size;

    @Override
    public void configQueryBuilder(QueryBuilder queryBuilder) {

    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
