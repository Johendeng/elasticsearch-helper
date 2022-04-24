package org.pippi.elasticsearch.helper.test.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.EsHelperSampleApplication;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.AggAccountQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.SimpleAccountQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountAggResult;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountEntity;
import org.pippi.elasticsearch.helper.spring.repository.mapper.EsAccountMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * EsAccountMapperTest
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class EsAccountMapperTest {

	@Resource
	private EsAccountMapper esAccountMapper;

	@Test
	public void testSimpleQuery() {
		SimpleAccountQueryParam param = new SimpleAccountQueryParam();
		param.setState("DE");
		param.setAddress("River Street");
//		RangeParam range = new RangeParam();
//		range.setLeft(25);
//		range.setRight(35);
//		param.setAge(range);
		param.setFuzzyField("Bates");
		BaseResp<AccountEntity> resp = esAccountMapper.queryByParam(param);
		System.out.println(SerializerUtils.parseObjToJson(resp));
	}

	@Test
	public void testAggQuery() {
		AggAccountQueryParam param = new AggAccountQueryParam();
		param.setCity("Nogal");
		AccountAggResult res = esAccountMapper.aggByParam(param);
		System.out.println(SerializerUtils.parseObjToJson(res));
	}

	@Test
	public void testAggQueryAnn() {
		AggAccountQueryParam param = new AggAccountQueryParam();
		AccountAggResult res = esAccountMapper.aggByParamAnn(param);
		System.out.println(SerializerUtils.parseObjToJson(res));
	}
}
