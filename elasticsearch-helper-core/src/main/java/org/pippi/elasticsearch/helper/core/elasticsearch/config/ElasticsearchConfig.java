package org.pippi.elasticsearch.helper.core.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * 描述
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/10
 */
@Configurable
public class ElasticsearchConfig {



	/**
	 *  elasticsearch 客户端实例
	 * @return
	 */
	@Bean
	public RestHighLevelClient restHighLevelClient(){




		return null;
	}


}
