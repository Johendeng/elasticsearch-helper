package org.pippi.elasticsearch.helper.core.handler;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.GeoPolygon;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.GeoPolygonQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * GeoPolygonQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/16
 */
@EsQueryHandle(GeoPolygon.class)
public class GeoPolygonQueryHandler extends AbstractQueryHandler<GeoPolygonQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoPolygonQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        //todo 参数未创建
        GeoPolygonQueryBuilder queryBuilder = QueryBuilders.geoPolygonQuery(queryDes.getField(), Lists.newArrayList());
        queryBuilder.boost(queryDes.getBoost());
        searchHelper.chain(queryBuilder);
        return null;
    }
}
