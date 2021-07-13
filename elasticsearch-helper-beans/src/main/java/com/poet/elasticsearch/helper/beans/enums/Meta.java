package com.poet.elasticsearch.helper.beans.enums;

/**
 * Project Name:elasticsearch-helper
 * File Name:Meta
 * Package Name:com.poet.elasticsearch.helper.enums
 * Date:2021/4/30 12:01
 * Author:JohenTeng
 * Description:
 */
public enum  Meta {

    KEYWORD("keyword", 1),
    TEXT("text", 2),

    INTEGER("integer",3),
    LONG("long",4),
    SHORT("short",5),
    BYTE("byte",6),

    DOUBLE("double",7),
    FLOAT("float",8),
    HALF_FLOAT("half_float",9),
    SCALED_FLOAT("scaled_float",10),

    DATE("date",100),
    RANGE("range",101),
    BINARY("binary",102),

    ARRAY("array",201),
    OBJECT("object",202),
    NESTED("nested",203),
    GEO_POINT("geo_point",204),
    GEO_SHAPE("geo_shape",205),
    IP("ip",206),
    TOKEN_COUNT("token_count",207),

    UN_COVER("un_cover", -1);
    ;



    private String type;
    private int code;

    Meta(String type, int code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }
}
