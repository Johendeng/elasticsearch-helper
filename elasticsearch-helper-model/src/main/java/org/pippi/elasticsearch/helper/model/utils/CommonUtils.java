package org.pippi.elasticsearch.helper.model.utils;

import com.google.common.base.CaseFormat;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * CommonUtils
 *
 * @author JohenTeng
 * @date 2022/5/10
 */
public class CommonUtils {

    /**
     * 封装参数判断逻辑
     *  集成3种类型的判空逻辑
     * @param obj 目标对象
     * @param <T>
     * @return
     */
    public static <T> Optional<T> optional(T obj) {
        return Optional.ofNullable(obj).filter(
                o -> {
                    if (o.getClass().isArray()) {
                        return ArrayUtils.isNotEmpty((Object[]) o);
                    }
                    if (Collection.class.isAssignableFrom(o.getClass())) {
                        return CollectionUtils.isNotEmpty((Collection<?>) o);
                    }
                    if (o instanceof String) {
                        return StringUtils.isNotBlank(o.toString());
                    }
                    return true;
                }
        );
    }


    public static <T extends Number>void filterIfPresent(T obj, Predicate<T> filter, Consumer<T> consumer) {
        Optional.ofNullable(obj).filter(filter).ifPresent(consumer);
    }


    /**
     * 封装参数判断逻辑
     *  集成3种类型的判空逻辑
     *  如果不为空， do something ... ...
     * @param obj 目标对象
     * @param <T>
     */
    public static <T>void optionalIfPresent(T obj, Consumer<T> consumer) {
        CommonUtils.optional(obj).ifPresent(consumer);
    }

    public static String toLowerUnderscore(String name) {
        if (name == null) {
            return "";
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    public static String toLowerCamel(String name) {
        if (name == null) {
            return "";
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
    }
}
