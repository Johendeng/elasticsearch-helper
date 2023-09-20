package org.pippi.elasticsearch.test.repository.entity;

import lombok.Data;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

import java.io.Serializable;

/**
 * AccountEntity
 *
 * @author JohenTeng
 * @date 2021/12/6
 */
@Data
@EsIndex("account")
public class AccountEntity extends EsEntity implements Serializable {

	private Integer accountNumber;

	private Integer balance;

	private String firstname;

	private String lastname;

	private Integer age;

	private String gender;

	private String address;

	private String employer;

	private String email;

	private String city;

	private String state;
}
