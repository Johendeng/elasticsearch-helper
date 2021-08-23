package org.pippi.elasticsearch.helper.core.engine;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.beans.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/17
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class EsQueryEngine {

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


    /**
     * @param queryViewObj
     * @param visitParent
     * @return
     */
    public static AbstractEsRequestHolder execute(Object queryViewObj, boolean visitParent) {

        QueryAnnParser translator = QueryAnnParser.instance();
        EsQueryIndexBean indexQueryBean = translator.getIndex(queryViewObj);
        List<EsQueryFieldBean> queryDesList = translator.read(queryViewObj, visitParent);
        AbstractEsRequestHolder helper = AbstractEsRequestHolder.builder()
                                                                .indexName(indexQueryBean.getIndexName())
                                                                .queryModel(indexQueryBean.getEsQueryModel())
                                                                .build();
        for (EsQueryFieldBean queryDes : queryDesList) {
            String queryKey = queryDes.getQueryType();
            AbstractQueryHandler queryHandle = QUERY_HANDLE_MAP.get(queryKey);
            queryHandle.execute(queryDes, helper);
        }
        return helper;
    }


    public void queryHandleScan() {

        LinkedList<String> packageList = Lists.newLinkedList();
        packageList.add(_BASE_SCAN_PACKAGE);

        String extHandlePath = System.getProperty(_EXT_DEFINE_QUERY_HANDLE_KEY);
        String[] packages = extHandlePath.split(",");

        List<String> extPackageList = Arrays.stream(Optional.ofNullable(packages).orElse(new String[0]))
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
            EsQueryEngine.addHandleClazz(handleName, targetClazz);

        }

    }


}
