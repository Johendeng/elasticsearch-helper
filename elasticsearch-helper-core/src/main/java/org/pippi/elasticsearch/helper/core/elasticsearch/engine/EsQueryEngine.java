//package org.pippi.elasticsearch.helper.core.elasticsearch.engine;
//
//import com.google.common.collect.Maps;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 描述
// *
// * @author dengtianjia@fiture.com
// * @date 2021/8/9
// */
//public class EsQueryEngine {
//
//	private static final Map<String, AbstractQueryHandle> _QUERY_HANDLES = Maps.newHashMap();
//
//	/**
//	 * handle instance map
//	 *
//	 * @param handleName
//	 * @param handler
//	 */
//	public static void addHandle(String handleName, AbstractQueryHandle handler) {
//		_QUERY_HANDLES.put(handleName, handler);
//	}
//
//	/**
//	 * @param queryViewObj
//	 * @param visitParent
//	 * @return
//	 */
//	public static AbstractEsRequestHolder execute(Object queryViewObj, boolean visitParent) {
//		EsQueryBeanParser translator = EsQueryBeanParser.instance();
//		List<EsQueryFieldBean> queryDesList = translator.read(queryViewObj, visitParent);
//		EsQueryIndexBean indexBean = translator.getIndex(queryViewObj);
//
//		AbstractEsRequestHolder helper =
//			AbstractEsRequestHolder.builder().define(indexBean.getIndexName(), indexBean.getEsQueryModel())
//				.build();
//
//		for (EsQueryFieldBean queryDes : queryDesList) {
//			String queryKey = queryDes.getQueryType();
//			AbstractQueryHandle queryHandle = _QUERY_HANDLES.get(queryKey);
//			queryHandle.execute(queryDes, helper);
//		}
//		return helper;
//	}
//
//}
