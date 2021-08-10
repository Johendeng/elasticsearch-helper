package org.pippi.elasticsearch.helper.core.elasticsearch.enums;

/**
 * 描述
 *
 * @author kelong@fiture.com
 * @date 2021/4/4
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
