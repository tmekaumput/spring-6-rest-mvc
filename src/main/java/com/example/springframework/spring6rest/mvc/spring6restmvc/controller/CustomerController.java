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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity newCustomer(@RequestBody Customer customer) {
        log.debug("New customer : " + customer.getCustomerName());

        Customer savedCustomer = customerService.newCustomer(customer);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity updateCustomer(@PathVariable("id")Integer id,@RequestBody Customer customer) {
        log.debug("Update customer : " + id);

        customerService.updateCustomer(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public Customer getCustomerById(@PathVariable("id") Integer id) {
        log.debug("Retrieving customer : " + id);

        return customerService.getCustomerById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCustomerById(@PathVariable("id") Integer id) {
        log.debug("Deleting customer : " + id);

        customerService.deleteCustomer(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
