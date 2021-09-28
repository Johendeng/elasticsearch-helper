package org.pippi.elasticsearch.helper.core.helper;

import org.elasticsearch.common.unit.Fuzziness;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;

/**
 * ExtConfig
 *  define the extend detail configuration of different query,
 *  for example: match, multi_match
 * @author JohenTeng
 * @date 2021/9/22
 */
@Deprecated
public class ExtConfig {

    private FuzzinessEnum fuzzinessEnum;

    private Integer   prefixLength;
    private Integer   maxExpansions;
    private String    analyzer;
    private Boolean   lenient;
    private Fuzziness fuzziness;

    public static ExtConfig def(){
        return new ExtConfig();
    }

    public ExtConfig fuzzyType(FuzzinessEnum fuzzinessEnum) {
        this.fuzzinessEnum = fuzzinessEnum;
        return this;
    }

    public ExtConfig prefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public ExtConfig maxExpansions(Integer maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public ExtConfig analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public ExtConfig lenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public void setFuzzy(FuzzinessEnum fuzzinessEnum) {
        this.fuzzinessEnum = fuzzinessEnum;
    }

    public void setFuzziness(Fuzziness fuzziness) {
        this.fuzziness = fuzziness;
    }

    @Override
    public String toString() {
        return SerializerUtils.parseObjToJsonSkipNull(this);
    }

    public static ExtConfig read(String extendJson){
        ExtConfig extConfig = SerializerUtils.jsonToBean(extendJson, ExtConfig.class);
        FuzzinessEnum fuzzinessEnum = extConfig.fuzzinessEnum;
        if (fuzzinessEnum != null) {
            extConfig.setFuzziness(fuzzinessEnum.getFuzziness());
        }
        return extConfig;
    }

}
