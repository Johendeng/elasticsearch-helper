//package org.pippi.elasticsearch.helper.core.elasticsearch.engine.parser;
//
//import com.google.common.collect.Lists;
//import org.apache.commons.lang3.StringUtils;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
///**
// * 描述
// *
// * @author dengtianjia@fiture.com
// * @date 2021/8/9
// */
//public class EsQueryBeanParser {
//
//
//	private volatile static EsQueryBeanParser INSTANCE ;
//
//	private EsQueryBeanParser(){}
//
//	public static EsQueryBeanParser instance(){
//		if (INSTANCE == null) {
//			synchronized (INSTANCE) {
//				if (INSTANCE == null) {
//					return new EsQueryBeanParser();
//				}
//			}
//		}
//		return INSTANCE;
//	}
//
//	/**
//	 *  读取查询索引
//	 * @param view
//	 * @return
//	 */
//	public EsQueryIndexBean getIndex(Object view) {
//		Class<?> clazz = view.getClass();
//		EsQueryIndex ann = clazz.getAnnotation(EsQueryIndex.class);
//		if (ann == null){
//			throw new RuntimeException("undefine query-index @EsQueryIndex");
//		}
//		String index = ann.index();
//		EsQueryModel model = ann.model();
//		EsQueryIndexBean res = new EsQueryIndexBean(index, model);
//		return res;
//	}
//
//	/**
//	 *  read query-view-pojo define by user,
//	 *  support java-base type, collections and
//	 *  @link
//	 *
//	 * @param view
//	 * @param visitParent
//	 * @return
//	 */
//	public List<EsQueryFieldBean> read(Object view, boolean visitParent) {
//		Class<?> clazz = view.getClass();
//
//		List<Field> fieldList = this.getFields(clazz, visitParent);
//		List<EsQueryFieldBean> queryDesList = Lists.newArrayListWithCapacity(fieldList.size());
//		for (Field field : fieldList) {
//			if (field.isAnnotationPresent(EsQueryFiled.class)) {
//				EsQueryFieldBean queryDes = this.mapFieldAnn(field, view);
//				if (null != queryDes) {
//					queryDesList.add(queryDes);
//				}
//			}
//		}
//		return queryDesList;
//	}
//
//	private List<Field> getFields(Class<?> clazz, boolean visitParent) {
//		if (visitParent) {
//			return getFields(clazz, Lists.newArrayList());
//		}
//		Field[] fieldArr = clazz.getDeclaredFields();
//		return Lists.newArrayList(fieldArr);
//	}
//
//	private List<Field> getFields(Class<?> clazz, List<Field> callBackList) {
//		Field[] fieldArr = clazz.getDeclaredFields();
//		callBackList.addAll(Arrays.asList(fieldArr));
//		if (!(clazz = clazz.getSuperclass()).equals(Object.class)) {
//			getFields(clazz, callBackList);
//		}
//		return callBackList;
//	}
//
//	private EsQueryFieldBean mapFieldAnn(Field field, Object viewObj) {
//
//		try {
//			EsQueryFieldBean queryDes = new EsQueryFieldBean<>();
//
//			Class<?> fieldType = field.getType();
//			field.setAccessible(true);
//			Object val = field.get(viewObj);
//
//			/**
//			 *  如果对象参数未赋值则返回 null
//			 */
//			if (val == null) {
//				return null;
//			}
//
//			if (fieldType.isPrimitive() || fieldType.equals(String.class)) {
//				queryDes.setValue(val);
//			}
//
//			if (val instanceof Collection && ! (val instanceof Map)) {
//				ParameterizedType genericType = (ParameterizedType) field.getGenericType();
//				Type[] actualTypeArguments = genericType.getActualTypeArguments();
//
//				if (actualTypeArguments.length > 1 || actualTypeArguments.length < 1 ) {
//					throw new RuntimeException("Just support single parameterized-type");
//				}
//
//				Type parameterizationType = actualTypeArguments[0];
//				Class actualClazz = Class.forName(parameterizationType.getTypeName());
// 				if (!(actualClazz.isPrimitive() || actualClazz.equals(String.class)) ) {
//					throw new RuntimeException("Just support Collection<@JavaBaseType>");
//				}
//				queryDes.setValues( ((Collection)val).toArray() );
//			}
//
//			if (val instanceof ComplexDefine) {
//				queryDes.setValue(val);
//			}
//
//			EsQueryFiled ann = field.getAnnotation(EsQueryFiled.class);
//
//			LogicConnector logicConnector = ann.logicConnector();
//			if (logicConnector == null) {
//				throw new RuntimeException("逻辑连接符不能为空");
//			}
//			queryDes.setLogicConnector(logicConnector);
//
//			String column = ann.name();
//			if (StringUtils.isBlank(column)) {
//				column = field.getName();
//			}
//
//			String query = ann.queryKey();
//			if (StringUtils.isBlank(query)) {
//				query =  ann.queryType().getQuery();
//			}
//			if (StringUtils.isBlank(query)) {
//				throw new RuntimeException("QUERY-TYPE missing, it's necessary");
//			}
//
//			String meta = ann.metaStringify();
//			if (StringUtils.isBlank(meta)) {
//				meta =  ann.meta().getType();
//			}
//			if (StringUtils.isBlank(meta)){
//				throw new RuntimeException("META-TYPE missing, it's necessary");
//			}
//
//
//			String script = ann.script();
//			String extendDefine = ann.extendDefine();
//
//			queryDes.setColumn(column);
//			queryDes.setQueryType(query);
//			queryDes.setMeta(meta);
//			queryDes.setScript(script);
//			queryDes.setExtendDefine(extendDefine);
//
//
//			return queryDes;
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException("unable reach target field ", e);
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("未能发现加载的类", e);
//		}
//	}
//
//
//
//
//}
