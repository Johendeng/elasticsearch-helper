package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SourceOrder;
import org.pippi.elasticsearch.helper.core.beans.query.SourceOrderQueryBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.Objects;

/**
 * describe doc's sort type,
 * simple force sort / sort by script
 * 描述召回文档的排序方式
 * 简单强制排序 / 通过脚本进行排序
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsQueryHandle(SourceOrder.class)
public class SourceSortQueryHandler extends AbstractQueryHandler<SourceOrderQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<SourceOrderQueryBean> queryDes, AbstractEsSession searchHelper) {
        SearchSourceBuilder source = searchHelper.getSource();
        SourceOrderQueryBean sourceOrderBean = queryDes.getExtBean();
        String script = sourceOrderBean.getScript();
        SortOrder sortOrder = sourceOrderBean.getSortOrder();
        ScriptSortBuilder.ScriptSortType sortType = sourceOrderBean.getSortType();
        // 排序 脚本排序优先级高于自定义强制排序
        if (StringUtils.isNotBlank(script)) {
            source.sort(SortBuilders.scriptSort(new Script(script), sortType).order(sortOrder));
            return null;
        }
        if (Objects.nonNull(sortOrder)) {
            source.sort(queryDes.getField(), sortOrder);
        }
        return null;
    }
}
