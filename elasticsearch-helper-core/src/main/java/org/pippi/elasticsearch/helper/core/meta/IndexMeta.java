package org.pippi.elasticsearch.helper.core.meta;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.core.utils.IndexMappingReader;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JohenDeng
 * @date 2023/9/8
 **/
public class IndexMeta {

    private final Map<String, FieldMeta> fieldMetaMap = new ConcurrentHashMap<>();

    private String indexName;

    public Map<String, FieldMeta> getFieldMap() {
        return this.fieldMetaMap;
    }

    public IndexMeta() {
    }

    public IndexMeta(String indexName) {
        this.indexName = indexName;
    }

    @SuppressWarnings("unchecked")
    public static IndexMeta parse(String indexName, Class<?> clazz, Map<String, Object> remoteMapping) {
        IndexMeta meta = new IndexMeta(indexName);
        List<Field> fieldList = ReflectionUtils.getFields(clazz, true);
        Map<String, Object> properties = (Map<String, Object>) remoteMapping.getOrDefault("properties", Maps.newLinkedHashMap());
        for (Field field : fieldList) {
            String fieldName = field.getName();
            String mapFieldName = EsHelperConfiguration.mapUnderscoreToCamelCase ? EsBeanFieldTransUtils.camelify(fieldName) : fieldName;
            EsField esField = field.getAnnotation(EsField.class);
            FieldMeta metaBean = new FieldMeta();
            if (esField != null) {
                metaBean.setField(mapFieldName = StringUtils.isNotBlank(esField.name()) ? esField.name() : mapFieldName);
            }
            Object typeObj = properties.get(mapFieldName);
            if (typeObj != null) {
                Map<String, Object> typeMap = (Map<String, Object>) typeObj;
                EsMeta esMeta = IndexMappingReader.readType(typeMap);
                metaBean.setType(esMeta);
                if (esMeta.equals(EsMeta.TEXT)) {
                    String extension = IndexMappingReader.readTextKeyWordSymbol(typeMap);
                    metaBean.setExtension(extension);
                }
            }
            meta.fieldMetaMap.put(fieldName, metaBean);
        }
        return meta;
    }

    public static class FieldMeta {

        private String field;

        private EsMeta type;

        private String extension;

        public FieldMeta() {
        }

        public FieldMeta(String field, EsMeta type, String extension) {
            this.field = field;
            this.type = type;
            this.extension = extension;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public EsMeta getType() {
            return type;
        }

        public void setType(EsMeta type) {
            this.type = type;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    public Map<String, FieldMeta> getFieldMetaMap() {
        return fieldMetaMap;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
