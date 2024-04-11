package com.example.springframework.spring6rest.mvc.spring6restmvc.controller;

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Customer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.mappers.CustomerMapper;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.CustomerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
 * @Author tmekaumput
 * @Date 8/4/24 5:55 pm
 *
 */

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void listCustomers() {
        List<CustomerDTO> customers = customerController.getCustomers();

        assertThat(customers.size()).isEqualTo(3);

    }

    @Test
    @Transactional
    @Rollback
    void createCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New customer")
                .build();

        ResponseEntity responseEntity = customerController.newCustomer(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void createCustomerTooLongName() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New customer 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
                .build();

        assertThrows(ConstraintViolationException.class, () -> customerController.newCustomer(customerDTO));
    }

    @Test
    void getCustomerById() {
        CustomerDTO customer = customerController.getCustomerById(1);

        assertThat(customer).isNotNull();
    }

    @Test
    void getCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(100));
    }

    @Test
    @Rollback
    @Transactional
    void emptyCustomers() {
        customerRepository.deleteAll();

        List<CustomerDTO> customers = customerController.getCustomers();

        assertThat(customers).isEmpty();
    }

    @Test
    @Rollback
    @Transactional
    void updateCustomer() {
        final String updatedCustomerName = "Updated customer name";

        Customer existingCustomer = customerRepository.findAll().get(0);

        CustomerDTO inputCustomer = customerMapper.customerToCustomerDto(existingCustomer);
        inputCustomer.setId(null);
        inputCustomer.setVersion(null);

        inputCustomer.setCustomerName(updatedCustomerName);
        ResponseEntity responseEntity = customerController.updateCustomer(existingCustomer.getId(), inputCustomer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(existingCustomer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(updatedCustomerName);
    }

    @Test
    void updateCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateCustomer(100, CustomerDTO.builder().build()));
    }

    @Test
    @Rollback
    @Transactional
    void patchCustomer() {
        final String patchedCustomerName = "Patched customer name";

        Customer existingCustomer = customerRepository.findAll().get(0);

        CustomerDTO inputCustomer = customerMapper.customerToCustomerDto(existingCustomer);
        inputCustomer.setId(null);
        inputCustomer.setVersion(null);

        inputCustomer.setCustomerName(patchedCustomerName);
        ResponseEntity responseEntity = customerController.patchCustomerById(existingCustomer.getId(), inputCustomer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer patchedCustomer = customerRepository.findById(existingCustomer.getId()).get();
        assertThat(patchedCustomer.getCustomerName()).isEqualTo(patchedCustomerName);
    }

    @Test
    void patchedCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.patchCustomerById(100, CustomerDTO.builder().build()));
    }

    @Test
    @Rollback
    @Transactional
    void deleteCustomer() {
        Customer existingCustomer =  customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomerById(existingCustomer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(existingCustomer.getId())).isEmpty();
    }

    @Test
    void deleteCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(100));

    }
}