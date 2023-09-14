package org.pippi.elasticsearch.test.repository.mapper;

import org.pippi.elasticsearch.helper.core.wrapper.mapper.EsBaseMapper;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsMapper;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Nested;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Term;
import org.pippi.elasticsearch.test.repository.entity.UserEntity;

import java.util.List;

/**
 * @author JohenDeng
 * @date 2023/9/5
 **/
@EsMapper
public interface UserMapper extends EsBaseMapper<UserEntity> {

    @EsAnnQueryIndex(index = "user")
    List<UserEntity> selectByAccount(@Term Integer accountNumber);

    @EsAnnQueryIndex(index = "user")
    List<UserEntity> selectByNested(@Nested(path = "detail_info") UserEntity.DetailInfo param);
}
