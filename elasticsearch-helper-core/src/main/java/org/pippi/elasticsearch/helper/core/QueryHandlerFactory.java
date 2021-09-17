package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.core.handler
 * date:    2021/9/8
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class QueryHandlerFactory {

    private static final Logger log = LoggerFactory.getLogger(QueryHandlerFactory.class);

    /**
     * user define config, { System.getProperty(_EXT_DEFINE_QUERY_HANDLE_KEY) }
     * format : es.helper.ext.handles=com.***.loc1,com.***.loc2
     * just the package loc, but it's also support define a explicit path like：com.XXX.xxx.TestQueryHandle
     */
    private static final String _EXT_DEFINE_QUERY_HANDLE_KEY = "es.helper.ext.handles";

    private static final String _BASE_SCAN_PACKAGE = "org.pippi.elasticsearch.helper.core.handler";

    private static final Map<String, Class<? extends AbstractQueryHandler>> QUERY_HANDLE_CLAZZ_MAP = new HashMap<>();

    private static final Map<String, AbstractQueryHandler> QUERY_HANDLE_MAP = new HashMap<>();


    public static void addHandleClazz(String handleName, Class<? extends AbstractQueryHandler> clazz) {
        QUERY_HANDLE_CLAZZ_MAP.put(handleName, clazz);
    }

    /**
     *  handle instance map
     * @param handleName
     * @param handler
     */
    public static void addHandle(String handleName, AbstractQueryHandler handler) {
        QUERY_HANDLE_MAP.put(handleName, handler);
    }


    public AbstractQueryHandler getTargetHandleInstance(String handlerName){
        AbstractQueryHandler targetHandler = QUERY_HANDLE_MAP.get(handlerName);
        if (targetHandler == null) {
            throw new EsHelperQueryException("un-match given handleName");
        }
        return targetHandler;
    }


    public void queryHandleScan() {

        LinkedList<String> packageList = Lists.newLinkedList();
        packageList.add(_BASE_SCAN_PACKAGE);

        String extHandlePath = System.getProperty(_EXT_DEFINE_QUERY_HANDLE_KEY);
        String[] packages = extHandlePath.split(",");

        List<String> extPackageList = Arrays.stream(Optional.ofNullable(packages)
                                            .orElse(new String[0]))
                                            .filter(StringUtils::isNoneBlank)
                                            .collect(Collectors.toList());
        packageList.addAll(extPackageList);

        Reflections reflections = new Reflections(packageList);
        Set<Class<? extends AbstractQueryHandler>> subQueryClazz = reflections.getSubTypesOf(AbstractQueryHandler.class);

        for (Class<? extends AbstractQueryHandler> targetClazz : subQueryClazz) {

            boolean flag = targetClazz.isAnnotationPresent(EsQueryHandle.class);
            if (!flag) {
                throw new EsHelperConfigException("query handle have to ann by @EsQueryHandle");
            }
            EsQueryHandle ann = targetClazz.getAnnotation(EsQueryHandle.class);
            String handleName = ann.name();
            if (StringUtils.isBlank(handleName)) {
                handleName = ann.handleEnum().getQuery();
            }
            if (StringUtils.isBlank(handleName)) {
                throw new EsHelperConfigException("handle-name is undefine");
            }
            this.addHandleClazz(handleName, targetClazz);

            QUERY_HANDLE_MAP.put(handleName, this.getTargetHandleInstance(targetClazz));
        }
    }

    private AbstractQueryHandler getTargetHandleInstance(Class<? extends AbstractQueryHandler> targetClazz) {
        try {
            Constructor<? extends AbstractQueryHandler> constructor = targetClazz.getConstructor(new Class[0]);
            AbstractQueryHandler abstractQueryHandler = constructor.newInstance(new Object[0]);
            return abstractQueryHandler;
        } catch (NoSuchMethodException e) {
            throw new EsHelperQueryException("es query handler load fail, cause:", e);
        } catch (InstantiationException e) {
            throw new EsHelperQueryException("es query handler load fail, cause:", e);
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("es query handler load fail, cause:", e);
        } catch (InvocationTargetException e) {
            throw new EsHelperQueryException("es query handler load fail, cause:", e);
        }
    }

}
