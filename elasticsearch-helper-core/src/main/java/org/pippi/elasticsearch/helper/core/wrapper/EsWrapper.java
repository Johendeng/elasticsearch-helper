package org.pippi.elasticsearch.helper.core.wrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 仅支持 bool 查询
 *
 * @author JohenDeng
 * @date 2023/8/28
 **/
public abstract class EsWrapper<T> {

    public abstract T getEntity();

    protected EsConnector currentConnector = EsConnector.MUST;

    protected Map<String, Object> updateMap = Maps.newHashMap();

    protected List<EsQueryFieldBean> queryDesList = Lists.newArrayList();

    protected List<HighlightBuilder> highlightBuilderList = Lists.newArrayList();

    protected EsQueryIndexBean indexInfo;

    protected Map<EsConnector, LinkedList<QueryBuilder>> freeQueries = Maps.newHashMap();

    protected List<AggregationBuilder> aggList = Lists.newArrayList();

    protected void connector(EsConnector currentConnector) {
        this.currentConnector = currentConnector;
    }

    public List<EsQueryFieldBean> getQueryDesList() {
        return queryDesList;
    }

    public EsQueryIndexBean getIndexInfo() {
        return indexInfo;
    }

    public Map<String, Object> updateMap() {
        return updateMap;
    }

    public Map<EsConnector, LinkedList<QueryBuilder>> freeQueries() {
        return freeQueries;
    }

    public List<HighlightBuilder> highlightBuilderList() {
        return highlightBuilderList;
    }

    public List<AggregationBuilder> aggList() {
        return aggList;
    }
}
