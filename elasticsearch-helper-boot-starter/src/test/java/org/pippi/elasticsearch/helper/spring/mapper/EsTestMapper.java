package org.pippi.elasticsearch.helper.spring.mapper;

import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;
import org.pippi.elasticsearch.helper.spring.entity.params.TestSearchParam;
import org.pippi.elasticsearch.helper.spring.entity.result.TestRecord;

/**
 * EsTestMapper
 *
 * @author JohenTeng
 * @date 2021/9/30
 */
@EsHelperProxy
public interface EsTestMapper {

	/**
	 *  query records by intensity-field
	 * @param param mapping query param bean
	 * @return
	 */
	BaseResp<TestRecord> queryRecordByIntensity(TestSearchParam param);

}
