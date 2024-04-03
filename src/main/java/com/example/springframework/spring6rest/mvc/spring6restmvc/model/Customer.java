package com.example.springframework.spring6rest.mvc.spring6restmvc.model;
/*
 * @Author tmekaumput
 * @Date 3/4/24 9:55 pm
 *
 */

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Customer {
    private Integer id;
    private String customerName;
    private Integer version;
    private Date createdDate;
    private Date lastModifiedDate;
}
