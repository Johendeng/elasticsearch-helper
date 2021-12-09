package org.pippi.elasticsearch.helper.spring.repository.mapper;

import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.AggAccountQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.SimpleAccountQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountAggResult;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountEntity;

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
	BaseResp<AccountEntity> queryByParam(SimpleAccountQueryParam param);

	/**
	 * 测试聚合查询
	 */
	AccountAggResult aggByParam(AggAccountQueryParam param);
}
