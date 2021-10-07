package org.pippi.elasticsearch.helper.core.config;

import com.google.common.collect.Maps;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.util.Map;
import java.util.function.Supplier;

/**
 * GlobalEsQueryConfig
 *   config global query supplier, in order to define the configuration
 *   with annotation like
 *   {@link org.pippi.elasticsearch.helper.core.beans.annotation.query.HighLight#highLightKey()}
 * @author JohenTeng
 * @date 2021/9/22
 */
public final class GlobalEsQueryConfig {

    private static final String DEFAULT_KEY = "default";

    private static Map<String, Supplier<HighlightBuilder>> HIGH_LIGHT_MAP = Maps.newHashMap();


    public synchronized static void configHighLight(String key, Supplier<HighlightBuilder> supplier){
        HIGH_LIGHT_MAP.put(key, supplier);
    }

    public static HighlightBuilder highLight(){
        return HIGH_LIGHT_MAP.get(DEFAULT_KEY).get();
    }

    public static HighlightBuilder highLight(String key){
        return HIGH_LIGHT_MAP.get(key).get();
    }




}
