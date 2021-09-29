package org.pippi.elasticsearch.helper.sample.spring;

import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.pippi.elasticsearch.helper.sample.spring.beans.req.ContentSearchParam;
import org.pippi.elasticsearch.helper.sample.spring.beans.resp.Content;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;

import java.util.function.Function;

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
