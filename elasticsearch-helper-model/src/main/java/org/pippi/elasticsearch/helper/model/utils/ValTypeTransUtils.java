package org.pippi.elasticsearch.helper.model.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

/**
 * @author JohenDeng
 * @date 2023/9/26
 **/
public class ValTypeTransUtils {

    private static final String UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";


    public static Object trans(Object val, Field field) {
        Class<?> clazz = field.getType();
        String str = val.toString();
        if (clazz.equals(String.class)) {
            return str;
        } else if (clazz.equals(Integer.class)) {
            return checkAndTrans(val, Integer.class, Integer::parseInt);
        } else if (clazz.equals(Boolean.class)) {
            return checkAndTrans(val, Boolean.class, Boolean::parseBoolean);
        } else if (clazz.equals(Long.class)) {
            return checkAndTrans(val, Long.class, Long::parseLong);
        } else if (clazz.equals(Short.class)) {
            return checkAndTrans(val, Short.class, Short::parseShort);
        } else if (clazz.equals(Float.class)) {
            return checkAndTrans(val, Float.class, Float::parseFloat);
        } else if (clazz.equals(Double.class)) {
            return checkAndTrans(val, Double.class, Double::parseDouble);
        } else if (clazz.equals(Byte.class)) {
            return checkAndTrans(val, Byte.class, Byte::parseByte);
        } else if (clazz.equals(Character.class)) {
            return checkAndTrans(val, Character.class, s -> s.charAt(0));
        } else if (clazz.equals(Date.class)) {
            if (val.getClass().equals(Long.class)) {
                return new Date((Long) val);
            }
            SimpleDateFormat ft = new SimpleDateFormat(getDatePattern(field));
            try {
                return ft.parse(str);
            } catch (ParseException e) {
                ExceptionUtils.mpe("Date type cast error, target:%s", e, str);
            }
        } else if (clazz.equals(LocalDateTime.class)) {
            if (val.getClass().equals(Long.class)) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) val), ZoneId.systemDefault());
            }
            DateTimeFormatter ft = DateTimeFormat.forPattern(getDatePattern(field));
            return ft.parseLocalDateTime(str);
        } else {
            return SerializerUtils.jsonToBean(SerializerUtils.parseObjToJson(val), clazz);
        }
        return null;
    }

    private static String getDatePattern(Field field) {
        EsField fieldAnn = field.getAnnotation(EsField.class);
        String pattern = null;
        if (fieldAnn != null) {
            pattern = fieldAnn.format();
        }
        if (StringUtils.isNotBlank(pattern)) {
            return pattern;
        }
        return UTC;
    }

    private static Object checkAndTrans(Object val, Class<?> returnType, Function<String, ?> func) {
        if (val.getClass().equals(returnType)) {
            return val;
        }
        return func.apply(val.toString());
    }
}
