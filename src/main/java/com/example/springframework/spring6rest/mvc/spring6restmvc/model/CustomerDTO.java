package com.example.springframework.spring6rest.mvc.spring6restmvc.model;
/*
 * @Author tmekaumput
 * @Date 3/4/24 9:55 pm
 *
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CustomerDTO {

    private Integer id;
    private Integer version;

    @NotBlank
    @NotNull
    private String customerName;

    private Date createdDate;
    private Date lastModifiedDate;
}
