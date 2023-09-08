package org.pippi.elasticsearch.helper.core.meta;

import org.pippi.elasticsearch.helper.model.bean.EsEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JohenDeng
 * @date 2023/9/8
 **/
public class IndexMeta {

    private final Map<String, FieldMeta> fieldMetaMap = new ConcurrentHashMap<>();

    private String indexName;

    private Class<? extends EsEntity> mapClazz;

    public Map<String, FieldMeta> getFieldMap() {
        return this.fieldMetaMap;
    }



    public static class FieldMeta {

        private String field;

        private String metaType;

        private String extendPlaceholder;


        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMetaType() {
            return metaType;
        }

        public void setMetaType(String metaType) {
            this.metaType = metaType;
        }

        public String getExtendPlaceholder() {
            return extendPlaceholder;
        }

        public void setExtendPlaceholder(String extendPlaceholder) {
            this.extendPlaceholder = extendPlaceholder;
        }
    }

}
