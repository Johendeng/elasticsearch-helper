package org.pippi.elasticsearch.helper.model.utils.support;

import java.lang.reflect.AccessibleObject;
import java.security.PrivilegedAction;

/**
 * @author JohenDeng
 * @date 2023/9/11
 **/
public class SetAccessibleAction <T extends AccessibleObject> implements PrivilegedAction<T> {

    private final T obj;

    public SetAccessibleAction(T obj) {
        this.obj = obj;
    }

    @Override
    public T run() {
        obj.setAccessible(true);
        return obj;
    }
}
