package org.pippi.elasticsearch.helper.core.utils;

import org.pippi.elasticsearch.helper.model.enums.EsMeta;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/9/11
 **/
public class IndexMappingReader {

    private static final String TYPE_KEY = "type";

    private static final String TEXT_FIELD_KEY = "fields";

    public static EsMeta readType(Map<String, Object> prop) {
        Object typeName = prop.get(TYPE_KEY);
        if (typeName == null) {
            return EsMeta.KEYWORD;
        }
        return EsMeta.getEsMetaJavaClazz((String) typeName);
    }

    public static String readTextKeyWordSymbol(Map<String, Object> prop) {
        Object fieldObj = prop.get(TEXT_FIELD_KEY);
        if (fieldObj == null) {
            return null;
        }
        Map<String, LinkedHashMap> fieldMap = (Map<String, LinkedHashMap>) fieldObj;
        return fieldMap.entrySet().stream()
                .filter(e -> e.getValue().get(TYPE_KEY).equals(EsMeta.KEYWORD.getType()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
