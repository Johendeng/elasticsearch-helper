package org.pippi.elasticsearch.test.repository.mapper;

import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseResponseHook;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;
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
@EsHelperProxy
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
