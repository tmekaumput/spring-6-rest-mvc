package com.example.springframework.spring6rest.mvc.spring6restmvc.services;
/*
 * @Author tmekaumput
 * @Date 3/4/24 9:59 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer newCustomer(Customer customer);


    List<Customer> listCustomers();



    Customer getCustomerById(Integer id);
}
