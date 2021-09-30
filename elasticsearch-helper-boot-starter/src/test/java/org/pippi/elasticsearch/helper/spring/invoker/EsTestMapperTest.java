package org.pippi.elasticsearch.helper.spring.invoker;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.spring.EsHelperSampleApplication;
import org.pippi.elasticsearch.helper.spring.entity.params.TestSearchParam;
import org.pippi.elasticsearch.helper.spring.entity.result.TestRecord;
import org.pippi.elasticsearch.helper.spring.mapper.EsTestMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * EsTestMapperTest
 *
 * @author JohenTeng
 * @date 2021/9/30
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class EsTestMapperTest {

	@Resource
	private EsTestMapper esTestMapper;

	@Test
	public void testContextLoads() {
		TestSearchParam param = new TestSearchParam();
		RangeParam rangeParam = new RangeParam();
		rangeParam.setLeft(12);
		rangeParam.setRight(15);
		param.setIntensity(rangeParam);
		param.setTitle("高然hit课呈");
		BaseResp<TestRecord> baseResp = esTestMapper.queryRecordByIntensity(param);
		System.out.println(SerializerUtils.parseObjToJson(baseResp));
	}
}