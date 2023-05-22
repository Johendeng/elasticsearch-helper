package org.pippi.elasticsearch.helper.core.utils;

import java.lang.reflect.Field;

/**
 * @author JohenDeng
 * @date 2022/12/7
 **/
public class FieldStringifyUtils {


    public static String parseCamelCase(Field f) {
        String name = f.getName();

        return null;
    }

    public static class Demo {

        private String customerName;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
    }

}
