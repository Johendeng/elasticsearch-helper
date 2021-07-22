package org.pippi.elasticsearch.helper.sample.ddl;

import org.pippi.elasticsearch.helper.core.utils.IndexMappingUtils;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.sample.ddl
 * date:    2021/7/22
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class MappingGenTest {

    public static void main(String[] args) {
        IndexMappingUtils mappingUtils = new IndexMappingUtils();
        mappingUtils.export("new_record", null, NewsRecord.class);
    }


}
