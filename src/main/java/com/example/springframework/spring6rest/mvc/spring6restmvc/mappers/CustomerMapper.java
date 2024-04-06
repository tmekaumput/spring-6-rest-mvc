package com.example.springframework.spring6rest.mvc.spring6restmvc.mappers;
/*
 * @Author tmekaumput
 * @Date 6/4/24 7:43 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Customer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
