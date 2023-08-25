package org.pippi.elasticsearch.test.repository.entity.condition;

import org.pippi.elasticsearch.helper.model.param.RangeParam;
import org.pippi.elasticsearch.helper.model.EsConditionHandle;

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
        if (Objects.isNull(val)) {
            return false;
        }
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
