package org.pippi.elasticsearch.helper.model.enums;


import org.elasticsearch.common.unit.Fuzziness;


/**
 * FuzzyType
 *
 * @author     JohenTeng
 * @date      2021/9/22
 */
public enum FuzzinessEnum {

    /**
     * elasticsearch fuzziness type
     */
    AUTO(Fuzziness.AUTO),

    ZERO(Fuzziness.ZERO),

    ONE(Fuzziness.ONE),

    TWO(Fuzziness.TWO)
    ;

    private final Fuzziness fuzziness;

    FuzzinessEnum(Fuzziness fuzziness) {
        this.fuzziness = fuzziness;
    }

    public Fuzziness getFuzziness() {
        return fuzziness;
    }
}
