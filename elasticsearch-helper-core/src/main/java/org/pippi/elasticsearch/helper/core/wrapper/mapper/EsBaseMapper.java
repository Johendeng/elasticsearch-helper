package org.pippi.elasticsearch.helper.core.wrapper.mapper;


import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.pippi.elasticsearch.helper.core.wrapper.EsWrapper;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.param.EsPage;
import org.pippi.elasticsearch.helper.model.resp.AggRes;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * T 实体类
 * F 字段
 * Children 子引用
 * @author JohenDeng
 * @date 2023/8/28
 **/
public interface EsBaseMapper<T extends EsEntity> extends EsMapper<T> {

    int insert(T entity);

    BulkResponse insertBatch(Collection<T> dataList);

    void insertBatchAsyncBulk(Collection<T> dataList, int refreshSize, long waitTime, TimeUnit unit);

    int deleteById(Serializable id);

    BulkByScrollResponse delete(EsWrapper<T> queryEsWrapper);

    BulkResponse deleteBatchIds(Collection<? extends Serializable> idList);

    int updateById(T entity);

    BulkByScrollResponse update(T entity, EsWrapper<T> updateEsWrapper);

    T selectById(Serializable id);

    List<T> selectBatchIds(Collection<? extends Serializable> idList);

    T selectOne(EsWrapper<T> queryEsWrapper);

    Long selectCount(EsWrapper<T> queryEsWrapper);

    List<T> selectList(EsWrapper<T> queryEsWrapper);

    List<Map<String, Object>> selectMaps(EsWrapper<T> queryEsWrapper);

    <E extends EsPage<T>> E selectPage(E page, EsWrapper<T> queryEsWrapper);

    <E extends EsPage<Map<String, Object>>> E selectMapsPage(E page, EsWrapper<T> queryEsWrapper);

    AggRes selectAgg(EsWrapper<T> queryEsWrapper);

    SearchResponse selectOrgResp(EsWrapper<T> queryEsWrapper);
}
