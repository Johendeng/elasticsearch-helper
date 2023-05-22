package org.pippi.elasticsearch.test.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author JohenDeng
 * @date 2022/12/7
 **/
@Data
public class EcommerceEntity {

    private String category;

    private String currency;

    @JsonProperty("customer_birth_date")
    private Date customerBirthDate;

    @JsonProperty("customer_first_name")
    private String customerFirstName;

    @JsonProperty("customer_full_name")
    private String customerFullName;

    @JsonProperty("customer_gender")
    private String customerGender;

}
