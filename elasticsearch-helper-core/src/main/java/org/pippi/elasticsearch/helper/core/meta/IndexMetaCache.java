package org.pippi.elasticsearch.helper.core.meta;

import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JohenDeng
 * @date 2023/9/8
 **/
public class IndexMetaCache {

    private static final Logger log = LoggerFactory.getLogger("INDEX-META-CACHE");

    private static final Map<Class<? extends EsEntity>, IndexMeta> INDEX_META_MAP = new ConcurrentHashMap<>();

    private static RestHighLevelClient esClient;

    private IndexMetaCache globalIndexMetaCache;






}
