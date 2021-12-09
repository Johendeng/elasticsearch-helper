package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.SourceOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.SourceOrderQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * SourceSortQueryHandler
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsQueryHandle(SourceOrder.class)
public class SourceSortQueryHandler extends AbstractQueryHandler<SourceOrderQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<SourceOrderQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        SearchSourceBuilder source = searchHelper.getSource();
        SourceOrderQueryBean sourceOrderBean = queryDes.getExtBean();
        String script = sourceOrderBean.getScript();
        String scriptType = sourceOrderBean.getScriptType();
        String sort = sourceOrderBean.getSort();
        if (StringUtils.isNotBlank(scriptType) && StringUtils.isNotBlank(script)) {
            source.sort(queryDes.getField()).sort(SortBuilders.scriptSort(
                new Script(script),
                ScriptSortBuilder.ScriptSortType.fromString(scriptType)
            ));
            return null;
        }
        if (StringUtils.isNotBlank(sort)) {
            source.sort(queryDes.getField(), SortOrder.fromString(sort));
            return null;
        }
        source.sort(queryDes.getField(), SortOrder.ASC);
        return null;
    }
}
