package org.pippi.elasticsearch.test.repository.entity;

import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Gte;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.param.EsComplexParam;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/9/4
 **/
@Data
public class UserEntity extends EsEntity implements Serializable {

    private Integer accountNumber;

    private Integer balance;

    private GeoPoint loc;

    private DetailInfo detailInfo;

    private String name;

    @Data
    public static class DetailInfo implements EsComplexParam {

        @Gte
        private Integer age;

        private String ip;

        private GeoPoint loc;

        private String symbol;

        private String comment;
    }
}
