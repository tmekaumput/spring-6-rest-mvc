package com.example.springframework.spring6rest.mvc.spring6restmvc.controller;
/*
 * @Author tmekaumput
 * @Date 4/4/24 10:09 am
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.Customer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public Customer getCustomerById(@PathVariable("id") Integer id) {
        log.debug("Retrieving customer : " + id);

        return customerService.getCustomerById(id);
    }
}
