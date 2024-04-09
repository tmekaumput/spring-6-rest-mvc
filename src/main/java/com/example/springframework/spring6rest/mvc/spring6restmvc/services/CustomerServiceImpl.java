package com.example.springframework.spring6rest.mvc.spring6restmvc.services;
/*
 * @Author tmekaumput
 * @Date 3/4/24 10:00 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<Integer, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();

        CustomerDTO c1 = CustomerDTO.builder().id(1)
        .customerName("B1")
        .version(1)
        .createdDate(new Date())
        .lastModifiedDate(new Date()).build();

        CustomerDTO c2 = CustomerDTO.builder().id(2)
                .customerName("B2")
                .version(1)
                .createdDate(new Date())
                .lastModifiedDate(new Date()).build();

        CustomerDTO c3 = CustomerDTO.builder().id(3)
                .customerName("B3")
                .version(1)
                .createdDate(new Date())
                .lastModifiedDate(new Date()).build();

        customerMap.put(c1.getId(), c1);
        customerMap.put(c2.getId(), c2);
        customerMap.put(c3.getId(), c3);
    }

    @Override
    public CustomerDTO newCustomer(CustomerDTO customer) {
        CustomerDTO newCustomer = CustomerDTO.builder()
                .customerName(customer.getCustomerName())
                .id(customerMap.size() + 1)
                .createdDate(new Date())
                .build();

        return saveCustomer(newCustomer);
    }

    private CustomerDTO saveCustomer(CustomerDTO customer) {
        customer.setLastModifiedDate(new Date());
        customer.setVersion(customer.getVersion() == null ? 1: customer.getVersion()+1);
        customerMap.put(customer.getId(), customer);

        return customer;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Integer id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(Integer id, CustomerDTO customer) {
        CustomerDTO savedCustomer = getCustomerById(id).orElse(null);

        if(savedCustomer != null) {
            savedCustomer.setCustomerName(customer.getCustomerName());

            saveCustomer(savedCustomer);
        }

        return Optional.of(savedCustomer);
    }

    @Override
    public Boolean deleteCustomer(Integer id) {
        customerMap.remove(id);

        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(Integer id, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }

        saveCustomer(existing);

        return Optional.of(existing);
    }
}
