package com.example.springframework.spring6rest.mvc.spring6restmvc.services;
/*
 * @Author tmekaumput
 * @Date 3/4/24 10:00 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<Integer, Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        Customer c1 = Customer.builder().id(1)
        .customerName("B1")
        .version(1)
        .createdDate(new Date())
        .lastModifiedDate(new Date()).build();

        Customer c2 = Customer.builder().id(2)
                .customerName("B2")
                .version(1)
                .createdDate(new Date())
                .lastModifiedDate(new Date()).build();

        Customer c3 = Customer.builder().id(3)
                .customerName("B3")
                .version(1)
                .createdDate(new Date())
                .lastModifiedDate(new Date()).build();

        customerMap.put(c1.getId(), c1);
        customerMap.put(c2.getId(), c2);
        customerMap.put(c3.getId(), c3);
    }

    @Override
    public Customer newCustomer(Customer customer) {
        Customer newCustomer = Customer.builder()
                .customerName(customer.getCustomerName())
                .id(customerMap.size() + 1)
                .createdDate(new Date())
                .build();

        return saveCustomer(newCustomer);
    }

    private Customer saveCustomer(Customer customer) {
        customer.setLastModifiedDate(new Date());
        customer.setVersion(customer.getVersion() == null ? 1: customer.getVersion()+1);
        customerMap.put(customer.getId(), customer);

        return customer;
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public void updateCustomer(Integer id, Customer customer) {
        Customer savedCustomer = getCustomerById(id).orElse(null);

        if(savedCustomer != null) {
            savedCustomer.setCustomerName(customer.getCustomerName());

            saveCustomer(savedCustomer);
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerMap.remove(id);
    }

    @Override
    public void patchCustomer(Integer id, Customer customer) {
        Customer existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }

        saveCustomer(existing);
    }
}
