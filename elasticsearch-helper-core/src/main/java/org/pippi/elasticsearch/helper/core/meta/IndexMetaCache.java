package org.pippi.elasticsearch.helper.core.meta;

import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
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

    private IndexMetaCache globalIndexMetaCache;


    public void putMetaIfAbsent(Class<? extends EsEntity> eClazz) {
        if (INDEX_META_MAP.containsKey(eClazz)) {
            return;
        }
        String indexName = this.getIndexName(eClazz);



    }

    private String getIndexName(Class<? extends EsEntity> eClazz) {
        EsIndex indexAnn = eClazz.getAnnotation(EsIndex.class);
        String indexName = EsHelperConfiguration.mapUnderscoreToCamelCase ? EsBeanFieldTransUtils.camelify(eClazz.getSimpleName())
                : eClazz.getSimpleName();
        if (indexAnn != null) {
            if (StringUtils.isNotBlank(indexAnn.value())) {
                indexName = indexAnn.value();
            } else if (StringUtils.isBlank(indexName) && StringUtils.isNotBlank(indexAnn.name())) {
                indexName = indexAnn.name();
            }
        }
        return indexName;
    }


}
