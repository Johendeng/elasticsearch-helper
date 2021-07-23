package org.pippi.elasticsearch.helper.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 描述
 *
 * @author dengtianjia@fiture.com
 * @date 2021/7/23
 */
public class TimeUtils {

    public enum Format {

        YYYY_MM_DD_HH_MM_SS ("yyyy-MM-dd HH:mm:ss"),
        YYYY_MM_DD ("yyyy-MM-dd"),
        ;
        private String pattern;

        Format(String pattern) {
            this.pattern = pattern;
        }

    }

    private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);

    public static Date parse(String time, Format format){

        return null;
    }

}
