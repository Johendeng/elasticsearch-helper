package org.pippi.elasticsearch.helper.core.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;

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

}
