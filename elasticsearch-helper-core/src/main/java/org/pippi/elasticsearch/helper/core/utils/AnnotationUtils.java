package org.pippi.elasticsearch.helper.core.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.pippi.elasticsearch.helper.core.beans.exception.SerializeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AnnotationUtils
 *    annotation mapping to Map<String, Object>
 *    Action: annotation don't define property equals(), can't mapping to Map result,
 *            OBJECT_METHOD_FILTER({@link Annotation})'s methods will be excluded
 * author     JohenTeng
 * date      2021/9/24
 */
public class AnnotationUtils {

    private static final HashSet<String> OBJECT_METHOD_FILTER = Sets.newHashSet(
        Arrays.stream(Annotation.class.getMethods()).map(Method::getName).collect(Collectors.toSet())
    );

    public static Map<String, Object> toMap(Annotation ann){
        List<Method> annMethods = Arrays.stream(ann.getClass().getDeclaredMethods())
                .filter(m -> !OBJECT_METHOD_FILTER.contains(m.getName()))
                .collect(Collectors.toList());
        Map<String, Object> res = Maps.newHashMap();
        for (Method m : annMethods) {
            String key = m.getName();
            Object val = ReflectionUtils.methodInvoke(ann, m);
            res.put(key, val);
        }
        return res;
    }




}
