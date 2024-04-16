package com.example.springframework.spring6rest.mvc.spring6restmvc.repositories;

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.BeerOrder;
import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


/*
 * @Author tmekaumput
 * @Date 15/4/24 8:24 pm
 *
 */

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer customer;
    Beer beer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        beer = beerRepository.findAll().get(0);
    }

    @Test
//    @Rollback
    @Transactional
    void createBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test order")
                .customer(customer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

        System.out.println(savedBeerOrder.getId());
        System.out.println(savedBeerOrder.getCustomer());
        System.out.println(customer.getBeerOrders().size());
    }
}