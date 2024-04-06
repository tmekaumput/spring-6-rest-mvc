package com.example.springframework.spring6rest.mvc.spring6restmvc.services;
/*
 * @Author tmekaumput
 * @Date 3/4/24 9:59 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer newCustomer(Customer customer);


    List<Customer> listCustomers();



    Optional<Customer> getCustomerById(Integer id);

    void updateCustomer(Integer id, Customer customer);

    void deleteCustomer(Integer id);

    void patchCustomer(Integer id, Customer customer);
}
