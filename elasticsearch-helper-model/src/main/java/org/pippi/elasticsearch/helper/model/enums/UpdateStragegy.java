package org.pippi.elasticsearch.helper.model.enums;

import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
public enum UpdateStragegy {

    NOT_NULL("not_null"),

    NOT_BLANK("not_blank"),

    ANY("any"),
    ;

    private final String symbol;

    UpdateStragegy(String symbol) {
        this.symbol = symbol;
    }

    public static boolean isGolbalNotNull() {
        return StringUtils.equals(EsHelperConfiguration.globalUpdateStrategy, NOT_NULL.symbol);
    }

    public static boolean isGolbalNotBlank() {
        return StringUtils.equals(EsHelperConfiguration.globalUpdateStrategy, NOT_BLANK.symbol);
    }

    public static boolean isGolbalAny() {
        return StringUtils.equals(EsHelperConfiguration.globalUpdateStrategy, ANY.symbol);
    }
}
