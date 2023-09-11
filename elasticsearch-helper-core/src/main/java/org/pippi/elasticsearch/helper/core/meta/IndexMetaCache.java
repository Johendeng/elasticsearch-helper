package org.pippi.elasticsearch.helper.core.meta;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.pippi.elasticsearch.helper.core.EsRestClientFactory;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JohenDeng
 * @date 2023/9/8
 **/
public class IndexMetaCache {

    private static final Logger log = LoggerFactory.getLogger("INDEX-META-CACHE");

    private static final Map<Class<?>, IndexMeta> INDEX_META_MAP = new ConcurrentHashMap<>();

    private IndexMetaCache globalIndexMetaCache;

    public static IndexMeta getMetaInfo(Class<? extends EsEntity> eClazz) {
        IndexMeta indexMeta = INDEX_META_MAP.get(eClazz);
        if (indexMeta == null) {
            return loadMetaIfAbsent(eClazz);
        }
        return indexMeta;
    }

    public static IndexMeta.FieldMeta getMetaField(Class<?> eClazz, String filedName) {
        return INDEX_META_MAP.getOrDefault(eClazz, new IndexMeta())
                .getFieldMap()
                .getOrDefault(filedName, null);
    }

    public static IndexMeta loadMetaIfAbsent(Class<?> eClazz) {
        IndexMeta indexMeta;
        if ((indexMeta = INDEX_META_MAP.get(eClazz)) != null) {
            return indexMeta;
        }
        EsIndex indexAnn = eClazz.getAnnotation(EsIndex.class);
        String indexName = IndexMetaCache.getIndexName(indexAnn, eClazz);
        Map<String, Object> remoteMapping = IndexMetaCache.getRemoteMappingDefine(indexName, indexAnn.clientKey());
        indexMeta = IndexMeta.parse(indexName, eClazz, remoteMapping);
        INDEX_META_MAP.put(eClazz, indexMeta);
        return indexMeta;
    }

    private static String getIndexName(EsIndex indexAnn, Class<?> eClazz) {
        String indexName = EsHelperConfiguration.mapUnderscoreToCamelCase ? EsBeanFieldTransUtils.camelify(eClazz.getSimpleName())
                : eClazz.getSimpleName();
        if (indexAnn != null) {
            if (StringUtils.isNotBlank(indexAnn.value())) {
                return indexAnn.value();
            }
            if (StringUtils.isNotBlank(indexAnn.name())) {
                return indexAnn.name();
            }
        }
        return indexName;
    }

    private static Map<String, Object> getRemoteMappingDefine(String index, String clientKey) {
        RestHighLevelClient client = EsRestClientFactory.getClient(clientKey);
        GetMappingsRequest mappingReq = new GetMappingsRequest();
        mappingReq.indices(index);
        try {
            GetMappingsResponse mapResp = client.indices().getMapping(mappingReq, RequestOptions.DEFAULT);
            return mapResp.mappings().get(index).getSourceAsMap();
        } catch (IOException e) {
            log.error("get index-mapping-info from remote-error", e);
            return Maps.newHashMap();
        }
    }
}
