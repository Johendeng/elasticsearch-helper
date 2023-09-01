package org.pippi.elasticsearch.helper.lambda.mapper;

import org.pippi.elasticsearch.helper.lambda.wrapper.EsWrapper;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.param.EsPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/8/28
 **/
public interface EsBaseMapper<T extends EsEntity> extends EsMapper<T> {

    int insert(T entity);

    int deleteById(Serializable id);

    int deleteByMap(Map<String, Object> columnMap);

    int delete(EsWrapper<T> queryEsWrapper);

    int deleteBatchIds(Collection<? extends Serializable> idList);

    int updateById(T entity);

    int update(T entity, EsWrapper<T> updateEsWrapper);

    T selectById(Serializable id);

    List<T> selectBatchIds(Collection<? extends Serializable> idList);

    List<T> selectByMap(Map<String, Object> columnMap);

    T selectOne(EsWrapper<T> queryEsWrapper);

    Integer selectCount(EsWrapper<T> queryEsWrapper);

    List<T> selectList(EsWrapper<T> queryEsWrapper);

    List<Map<String, Object>> selectMaps(EsWrapper<T> queryEsWrapper);

    List<Object> selectObjs(EsWrapper<T> queryEsWrapper);

    <E extends EsPage<T>> E selectPage(E page, EsWrapper<T> queryEsWrapper);

    <E extends EsPage<Map<String, Object>>> E selectMapsPage(E page, EsWrapper<T> queryEsWrapper);
}
