package org.pippi.elasticsearch.helper.lambda.utils;

import java.util.LinkedHashMap;

/**
 * @author JohenDeng
 * @date 2023/9/18
 **/
public class FieldMap<F> extends LinkedHashMap<F, Float> {

    public static <F> FieldMap<F> init() {
        return new FieldMap<>();
    }

    public FieldMap<F> chainPut(F f, Float s) {
        this.put(f, s);
        return this;
    }
}
