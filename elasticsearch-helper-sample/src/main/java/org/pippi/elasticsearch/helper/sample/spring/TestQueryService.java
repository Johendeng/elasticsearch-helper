package org.pippi.elasticsearch.helper.sample.spring;

import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.sample.spring.beans.req.ContentSearchParam;
import org.pippi.elasticsearch.helper.sample.spring.beans.resp.Content;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;

/**
 * TestQueryService
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
@EsHelperProxy
public interface TestQueryService {

    /**
     *  query records by intensity-field
     * @param param mapping query param bean
     * @return
     */
    BaseResp<Content> queryRecordByIntensity(ContentSearchParam param);

}
