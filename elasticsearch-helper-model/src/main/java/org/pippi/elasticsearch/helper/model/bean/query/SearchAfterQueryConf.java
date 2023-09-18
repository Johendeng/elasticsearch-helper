package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * SearchAfterQueryBean
 *
 * @author JohenTeng
 * @date 2022/4/28
 */
public class SearchAfterQueryConf extends QueryConf {

    private SortOrder order = SortOrder.ASC;

    private Integer size = 10;

    public static SearchAfterQueryConf build() {
        return new SearchAfterQueryConf();
    }

    @Override
    public void configQueryBuilder(QueryBuilder queryBuilder) {

    }

    public SortOrder order() {
        return order;
    }

    public SearchAfterQueryConf setOrder(SortOrder order) {
        this.order = order;
        return this;
    }

    public Integer size() {
        return size;
    }

    public SearchAfterQueryConf setSize(Integer size) {
        this.size = size;
        return this;
    }
}
