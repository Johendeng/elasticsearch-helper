package org.pippi.elasticsearch.helper.core.config;

import com.google.common.collect.Maps;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;

import java.util.Map;
import java.util.function.Supplier;

/**
 * GlobalEsQueryConfig
 *   config global query supplier, in order to define the configuration
 *   with annotation like
 *   {@link Base#highLightKey()}
 * @author JohenTeng
 * @date 2021/9/22
 */
public final class GlobalEsQueryConfig {

    private static final String DEFAULT_KEY = "default";

    private static Map<String, Supplier<HighlightBuilder>> HIGH_LIGHT_MAP = Maps.newHashMap();

    public static Map<String, Supplier<? extends QueryBuilder>> DEFAULT_QUERY_BUILDERS = Maps.newHashMap();


    public static void configHighLight(String key, Supplier<HighlightBuilder> supplier){
        HIGH_LIGHT_MAP.put(key, supplier);
    }

    public static HighlightBuilder highLight(){
        return HIGH_LIGHT_MAP.get(DEFAULT_KEY).get();
    }

    public static HighlightBuilder highLight(String key){
        return HIGH_LIGHT_MAP.get(key).get();
    }

    public static void configDefaultQueryBuilders(String key, Supplier<? extends QueryBuilder> queryBuilderSupplier){
        DEFAULT_QUERY_BUILDERS.put(key, queryBuilderSupplier);
    }

    public static QueryBuilder getDefaultQueryBuilder(String key) {
        return DEFAULT_QUERY_BUILDERS.get(key).get();
    }



}
