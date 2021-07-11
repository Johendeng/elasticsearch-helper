package com.poet.elasticsearch.helper.core;

import com.poet.elasticsearch.helper.beans.annotation.EsIndex;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/5/13
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class IndexMapperTrans {


    /**
     *  translate target Index-mapper-class to initialize Index mapper-json
     * @param target
     * @return
     */
    public String handle(Class<?> target) {

        EsIndex indexDesInfo = target.getAnnotation(EsIndex.class);
         


        return null;
    }


}
