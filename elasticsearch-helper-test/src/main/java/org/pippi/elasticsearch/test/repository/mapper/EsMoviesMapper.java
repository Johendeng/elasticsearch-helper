package org.pippi.elasticsearch.test.repository.mapper;

import org.pippi.elasticsearch.helper.core.wrapper.mapper.EsBaseMapper;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsMapper;
import org.pippi.elasticsearch.test.repository.entity.Movies;

/**
 * @author JohenDeng
 * @date 2023/9/26
 **/
@EsMapper
public interface EsMoviesMapper extends EsBaseMapper<Movies> {


}
