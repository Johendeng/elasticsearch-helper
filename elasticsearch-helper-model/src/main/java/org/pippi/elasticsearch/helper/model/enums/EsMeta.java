package org.pippi.elasticsearch.helper.model.enums;


import java.util.*;

/**
 * Project Name:elasticsearch-helper
 * File Name:Meta
 * Package Name:com.poet.elasticsearch.helper.enums
 * @date 2021/4/30 12:01
 * @author JohenTeng
 */
public enum EsMeta {

    /**
     *
     */
    KEYWORD("keyword", 1, String.class),

    BOOLEAN("boolean",2, Boolean.class),

    INTEGER("integer",3, Integer.class),

    LONG("long",4, Long.class),

    SHORT("short",5, Short.class),

    BYTE("byte",6, Byte.class),

    DOUBLE("double",7, Double.class),

    FLOAT("float",8, Float.class),

    HALF_FLOAT("half_float",9, Float.class),

    SCALED_FLOAT("scaled_float",10, Float.class),

    TEXT("text", 11, String.class),

    DATE("date",100, Date.class),

    RANGE("range",101, Object.class),

    BINARY("binary",102, Object.class),

    OBJECT("object",202, Object.class),

    NESTED("nested",203, Object.class),
    GEO_POINT("geo_point",204, Object.class),

    GEO_SHAPE("geo_shape",205, Object.class),

    IP("ip",206, String.class),

    TOKEN_COUNT("token_count",207, Object.class),

    COMPLEX("complex", -1, Object.class),
    ;

    private final String type;
    private final int code;
    private final Class<?> clazz;

    EsMeta(String type, int code, Class<?> clazz) {
        this.type = type;
        this.code = code;
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    private static final Map<String, Class<?>> META_CLAZZ_MAP = new HashMap<>();

    static {
        Arrays.stream(EsMeta.values()).forEach( en ->
            META_CLAZZ_MAP.put(en.getType().toLowerCase(Locale.ROOT), en.getClazz())
        );
    }

    public static Class<?> getEsMetaJavaClazz(String type) {
        Class<?> clazz = META_CLAZZ_MAP.get(type.toLowerCase(Locale.ROOT));
        if (Objects.isNull(clazz)) {
            return Object.class;
        }
        return clazz;
    }
}
