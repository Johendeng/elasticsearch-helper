package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.SearchAfter;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.SearchAfterQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * SearchAfterQueryHandler
 *
 * @author JohenTeng
 * @date 2022/4/28
 */
@EsQueryHandle(SearchAfter.class)
public class SearchAfterQueryHandler extends AbstractQueryHandler<SearchAfterQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<SearchAfterQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        SearchAfterQueryBean extBean = queryDes.getExtBean();
        String fieldName = queryDes.getField();
        Object value = queryDes.getValue();
        SearchSourceBuilder source = searchHelper.getSource();
        source.sort(fieldName, extBean.getOrder()).searchAfter(new Object[]{value});
        source.size(extBean.getSize());
        return null;
    }
}
