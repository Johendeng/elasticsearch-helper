package org.pippi.elasticsearch.test.repository.mapper;

import org.pippi.elasticsearch.helper.model.annotations.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.model.annotations.hook.UseResponseHook;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsMapper;
import org.pippi.elasticsearch.test.repository.entity.params.AggAccountQueryParam;
import org.pippi.elasticsearch.test.repository.entity.params.SimpleAccountQueryParam;
import org.pippi.elasticsearch.test.repository.entity.result.AccountAggResult;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;

import java.util.List;

/**
 * EsAccountMapper
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsMapper
public interface EsAccountMapper {

	/**
	 * @param param simple es query
	 */
	List<AccountEntity> queryByParam(SimpleAccountQueryParam param);

	/**
	 * 测试聚合查询
	 */
	AccountAggResult aggByParam(AggAccountQueryParam param);

	@UseRequestHook("aggReqHook")
	@UseResponseHook("aggResRespHook")
	AccountAggResult aggByParamAnn(AggAccountQueryParam param);

}
