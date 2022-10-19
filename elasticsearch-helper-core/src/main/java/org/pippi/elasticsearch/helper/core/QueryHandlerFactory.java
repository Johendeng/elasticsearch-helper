package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.pippi.elasticsearch.helper.core.utils.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date     2021/9/8
 * @author   JohenTeng
 **/
public class QueryHandlerFactory {

    private static final Logger log = LoggerFactory.getLogger(QueryHandlerFactory.class);

    /**
     * user define config, { System.getProperty(_EXT_DEFINE_QUERY_HANDLE_KEY) }
     * format : es.helper.ext.handles=com.***.loc1,com.***.loc2
     * just the package loc, but it's also support define a explicit path like：com.XXX.xxx.TestQueryHandle
     */
    private static final String _EXT_DEFINE_QUERY_HANDLE_PROPERTY = "es.helper.ext.handle.packages";

    private static final String _BASE_SCAN_PACKAGE = "org.pippi.elasticsearch.helper.core.handler";

    private static final Map<String, Class<? extends AbstractQueryHandler>> QUERY_HANDLE_CLAZZ_MAP = new HashMap<>();

    private static final Map<String, AbstractQueryHandler> QUERY_HANDLE_MAP = new HashMap<>();

    private static final String BANNER_LOC = "pippi-banner.txt";
    private static final String PROP_LOC = "elasticsearch-helper.properties";

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

    public static AbstractQueryHandler getTargetHandleInstance(String handlerName){
        AbstractQueryHandler targetHandler = QUERY_HANDLE_MAP.get(handlerName);
        if (targetHandler == null) {
            throw new EsHelperQueryException("un-match given handleName");
        }
        return targetHandler;
    }

    public synchronized static void doQueryHandleScan() {

        LinkedList<String> packageList = Lists.newLinkedList();
        packageList.add(_BASE_SCAN_PACKAGE);
        // LOAD THE CONFIGURATION FROM SYSTEM_PROPERTY
        String extHandlePath =System.getProperty(_EXT_DEFINE_QUERY_HANDLE_PROPERTY, "");
        String[] packages = extHandlePath.split(",");

        List<String> extPackageList = Arrays.stream(Optional.ofNullable(packages)
                                            .orElse(new String[0]))
                                            .filter(StringUtils::isNoneBlank)
                                            .collect(Collectors.toList());
        log.info("load the handlers locations:\n {}", extPackageList.stream().collect(Collectors.joining("\n")));
        packageList.addAll(extPackageList);

        Reflections reflections = new Reflections(packageList);
        Set<Class<? extends AbstractQueryHandler>> subQueryClazz = reflections.getSubTypesOf(AbstractQueryHandler.class);

        for (Class<? extends AbstractQueryHandler> targetClazz : subQueryClazz) {
            boolean flag = targetClazz.isAnnotationPresent(EsQueryHandle.class);
            if (!flag) {
                throw new EsHelperConfigException("query handle have to ann by @EsQueryHandle");
            }
            EsQueryHandle ann = targetClazz.getAnnotation(EsQueryHandle.class);
            String[] handleName = ann.queryType();
            Set<String> handleNames = null;
            if (ArrayUtils.isEmpty(handleName)) {
                Class<? extends Annotation>[] handleAnnTypes = ann.value();
                handleNames = Arrays.stream(handleAnnTypes).map(Class::getSimpleName).collect(Collectors.toSet());
            } else {
                handleNames = Sets.newHashSet(handleName);
            }
            if (CollectionUtils.isEmpty(handleNames)) {
                throw new EsHelperConfigException("handle-name is undefine");
            }
            handleNames.forEach(name -> {
                QueryHandlerFactory.addHandleClazz(name, targetClazz);
                QUERY_HANDLE_MAP.put(name, QueryHandlerFactory.getTargetHandleInstance(targetClazz));
            });
        }
        String banner = readBanner();
        log.info(banner);
        log.info("es-helper-query-handler-scanner load handles:\n{}\n",
                QUERY_HANDLE_CLAZZ_MAP.entrySet().stream().map(
                        e -> "[\tes-helper] " + e.getKey() + ":" + e.getValue()
                ).collect(Collectors.joining("\n"))
        );
    }

    private static AbstractQueryHandler getTargetHandleInstance(Class<? extends AbstractQueryHandler> targetClazz) {
        return ReflectionUtils.newInstance(targetClazz);
    }

    private static String readBanner() {
        try {
            InputStream bannerStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BANNER_LOC);
            BufferedReader reader = new BufferedReader(new InputStreamReader(bannerStream));
            String banner = reader.lines().collect(Collectors.joining("\n"));
            String version = Optional.ofNullable(QueryHandlerFactory.class.getPackage().getImplementationVersion()).
                    orElse("unknow");
            banner = String.format(banner, version);
            return banner;
        } catch (Exception e) {
            return "";
        }
    }

    private static String getProperty(String fullKey) throws URISyntaxException, IOException {
        InputStream bannerStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROP_LOC);
        BufferedReader reader = new BufferedReader(new InputStreamReader(bannerStream));
        Properties properties = new Properties();
        properties.load(reader);
        return (String) properties.getOrDefault(fullKey, "");
    }

}
