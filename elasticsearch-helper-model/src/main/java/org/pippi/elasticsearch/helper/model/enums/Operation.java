package org.pippi.elasticsearch.helper.model.enums;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
public enum Operation {

    INSERT("created"),

    UPDATE("updated"),

    DELETE("deleted"),
    ;


    private final String excFlag;

    Operation(String excFlag) {
        this.excFlag = excFlag;
    }

    public String getExcFlag() {
        return excFlag;
    }
}
