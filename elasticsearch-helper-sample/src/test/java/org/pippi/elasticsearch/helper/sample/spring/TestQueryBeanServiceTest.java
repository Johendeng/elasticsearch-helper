package org.pippi.elasticsearch.helper.sample.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.sample.SampleApplication;
import org.pippi.elasticsearch.helper.sample.spring.beans.req.ContentSearchParam;
import org.pippi.elasticsearch.helper.sample.spring.beans.resp.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * TestQueryServiceTest
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class TestQueryBeanServiceTest {

    @Resource
    private TestQueryService testQueryService;

    @Test
    public void testQueryService(){
        ContentSearchParam param = new ContentSearchParam();
        RangeParam rangeParam = new RangeParam();
        rangeParam.setLeft(12);
        rangeParam.setRight(15);
        param.setIntensity(rangeParam);
//        param.setTitle("课呈");
        BaseResp<Content> baseResp = testQueryService.queryRecordByIntensity(param);
        System.out.println(SerializerUtils.parseObjToJson(baseResp));
    }

}