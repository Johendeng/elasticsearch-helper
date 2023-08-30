package org.pippi.elasticsearch.helper.model.config;

/**
 * @author JohenDeng
 * @date 2023/8/29
 **/
public class EsHelperConfiguration {
//    public static final String _EXT_DEFINE_QUERY_HANDLE_PROPERTY = "es.helper.ext.handle.packages";
//
//    public static final String ENABLE_LOG_OUT_PROPERTIES = "es.helper.queryLogOut.enable";
//
//    public static final String _MAP_UNDERSCORE_TO_CAMEL_CASE = "es.helper.configuration.map-underscore-to-camel-case";
//
//    public static final String _FIELD_STRATEGY = "es.helper.configuration.field-strategy";
    public static volatile boolean statementLogOut = true;

    public static volatile String extDefineQueryHandlerProperty = "";

    public static volatile  boolean mapUnderscoreToCamelCase = true;

    public static volatile  String globalUpdateStrategy = "not_null";

    public static void setStatementLogOut(boolean statementLogOut) {
        EsHelperConfiguration.statementLogOut = statementLogOut;
    }

    public static void setExtDefineQueryHandlerProperty(String extDefineQueryHandlerProperty) {
        EsHelperConfiguration.extDefineQueryHandlerProperty = extDefineQueryHandlerProperty;
    }

    public static void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        EsHelperConfiguration.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    public static void setGlobalUpdateStrategy(String globalUpdateStrategy) {
        EsHelperConfiguration.globalUpdateStrategy = globalUpdateStrategy;
    }
}
