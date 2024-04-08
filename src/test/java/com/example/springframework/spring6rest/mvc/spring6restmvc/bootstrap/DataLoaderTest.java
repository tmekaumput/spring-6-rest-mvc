package com.example.springframework.spring6rest.mvc.spring6restmvc.bootstrap;
/*
 * @Author tmekaumput
 * @Date 8/4/24 3:29 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.BeerRepository;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DataLoaderTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        dataLoader = new DataLoader(beerRepository, customerRepository);
    }

    @Test
    void loadData() throws Exception {
        dataLoader.run(null);

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}
