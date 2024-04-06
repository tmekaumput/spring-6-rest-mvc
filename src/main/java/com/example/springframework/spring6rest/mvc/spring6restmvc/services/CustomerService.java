package com.example.springframework.spring6rest.mvc.spring6restmvc.services;
/*
 * @Author tmekaumput
 * @Date 3/4/24 9:59 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDTO newCustomer(CustomerDTO customer);


    List<CustomerDTO> listCustomers();



    Optional<CustomerDTO> getCustomerById(Integer id);

    void updateCustomer(Integer id, CustomerDTO customer);

    void deleteCustomer(Integer id);

    void patchCustomer(Integer id, CustomerDTO customer);
}
