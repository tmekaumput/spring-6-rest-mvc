package com.example.springframework.spring6rest.mvc.spring6restmvc.repositories;
/*
 * @Author tmekaumput
 * @Date 6/4/24 6:11 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
