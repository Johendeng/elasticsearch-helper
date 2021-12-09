package org.pippi.elasticsearch.helper.spring.repository.entity.condition;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.handler.EsConditionHandle;

import java.util.Objects;

/**
 * AgeUseCondition
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
public class AgeUseCondition implements EsConditionHandle<RangeParam> {

    @Override
    public boolean test(RangeParam val) {
        Object left = val.getLeft();
        if (Objects.nonNull(left)) {
            int leftVal = (int) left;
            if (leftVal >= 20) {
                return true;
            }
        }
        return false;
    }
}
