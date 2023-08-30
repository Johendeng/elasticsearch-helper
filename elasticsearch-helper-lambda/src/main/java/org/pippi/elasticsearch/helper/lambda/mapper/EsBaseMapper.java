package org.pippi.elasticsearch.helper.lambda.mapper;

import org.pippi.elasticsearch.helper.lambda.wrapper.Wrapper;
import org.pippi.elasticsearch.helper.model.param.EsPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/8/28
 **/
public interface EsBaseMapper<T> extends EsMapper<T> {

    int insert(T entity);

    int deleteById(Serializable id);

    int deleteByMap(Map<String, Object> columnMap);

    int delete(Wrapper<T> queryWrapper);

    int deleteBatchIds(Collection<? extends Serializable> idList);

    int updateById(T entity);

    int update(T entity, Wrapper<T> updateWrapper);

    T selectById(Serializable id);

    List<T> selectBatchIds(Collection<? extends Serializable> idList);

    List<T> selectByMap(Map<String, Object> columnMap);

    T selectOne(Wrapper<T> queryWrapper);

    Integer selectCount(Wrapper<T> queryWrapper);

    List<T> selectList(Wrapper<T> queryWrapper);

    List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper);

    List<Object> selectObjs(Wrapper<T> queryWrapper);

    <E extends EsPage<T>> E selectPage(E page, Wrapper<T> queryWrapper);

    <E extends EsPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<T> queryWrapper);
}
