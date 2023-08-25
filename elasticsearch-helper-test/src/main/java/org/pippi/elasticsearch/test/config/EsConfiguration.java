package org.pippi.elasticsearch.test.config;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.model.config.GlobalEsQueryConfig;

import org.pippi.elasticsearch.test.constants.HighLightKeys;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 1- 配置自定义的 highLight 对象生成器
 *
 * @author JohenTeng
 * @date 2021/9/30
 */
@Component
public class EsConfiguration implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 1 -配置自定义的 highLight 对象生成器
		GlobalEsQueryConfig.configHighLight(HighLightKeys.HTML, ()->
			SearchSourceBuilder.highlight().fragmentSize(10).numOfFragments(5).noMatchSize(5)
		);
		GlobalEsQueryConfig.configHighLight(HighLightKeys.DOC, ()->
			SearchSourceBuilder.highlight().fragmentSize(10).numOfFragments(5).noMatchSize(3)
		);
	}

}
