package org.pippi.elasticsearch.helper.core.beans.enums;


import org.elasticsearch.common.unit.Fuzziness;


/**
 * FuzzyType
 *
 * @author JohenTeng
 * @date 2021/9/22
 */
public enum Fuzzy {

    /**
     * elasticsearch fuzziness type
     */
    AUTO(Fuzziness.AUTO),
    ZERO(Fuzziness.ZERO),
    ONE(Fuzziness.ONE),
    TWO(Fuzziness.TWO)
    ;

    private Fuzziness fuzziness;

    Fuzzy(Fuzziness fuzziness) {
        this.fuzziness = fuzziness;
    }

    public Fuzziness getFuzziness() {
        return fuzziness;
    }
}
