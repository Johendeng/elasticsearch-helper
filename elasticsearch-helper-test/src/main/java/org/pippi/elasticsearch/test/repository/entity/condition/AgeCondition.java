package org.pippi.elasticsearch.test.repository.entity.condition;

import org.pippi.elasticsearch.helper.model.EsConditionHandle;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
public class AgeCondition implements EsConditionHandle<Integer> {

    @Override
    public boolean test(Integer val) {
        return val > 40;
    }
}
