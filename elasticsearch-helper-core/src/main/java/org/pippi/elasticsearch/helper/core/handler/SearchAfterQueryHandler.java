package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SearchAfter;
import org.pippi.elasticsearch.helper.model.bean.query.SearchAfterQueryConf;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * SearchAfterQueryHandler
 *
 * @author JohenTeng
 * @date 2022/4/28
 */
@EsQueryHandle(SearchAfter.class)
public class SearchAfterQueryHandler extends AbstractQueryHandler<SearchAfterQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<SearchAfterQueryConf> queryDes, AbstractEsSession searchHelper) {
        SearchAfterQueryConf extBean = queryDes.getExtBean();
        String fieldName = queryDes.getField();
        Object value = queryDes.getValue();
        SearchSourceBuilder source = searchHelper.getSource();
        source.sort(fieldName, extBean.order()).searchAfter(new Object[]{value});
        source.size(extBean.size());
        return null;
    }
}
