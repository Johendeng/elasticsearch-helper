package org.pippi.elasticsearch.helper.core.handler;

import com.google.common.collect.Maps;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.script.Script;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.ScriptQuery;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.ScriptQueryBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.Map;
import java.util.Objects;

/**
 * ScriptQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(ScriptQuery.class)
public class ScriptQueryHandler extends AbstractQueryHandler<ScriptQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<ScriptQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        ScriptQueryBean extBean = queryDes.getExtBean();
        Map params = Maps.newHashMap();
        Script script = new Script(extBean.getScriptType(), extBean.getLang(), extBean.getIdOrCode(), params);
        ScriptQueryBuilder scriptQueryBuilder = QueryBuilders.scriptQuery(script).boost(queryDes.getBoost());
        if (queryDes.getExtBean().isHasParams()) {
            if (queryDes.getValue() instanceof Map && Objects.nonNull(queryDes.getValue())) {
                params.putAll((Map) queryDes.getValue());
            } else {
                throw new EsHelperQueryException("@ScriptQuery's [hasParams].value is TRUE the Field have to define as [Map.class] and can't be null");
            }
        }
        searchHelper.chain(scriptQueryBuilder);
        return scriptQueryBuilder;
    }
}
