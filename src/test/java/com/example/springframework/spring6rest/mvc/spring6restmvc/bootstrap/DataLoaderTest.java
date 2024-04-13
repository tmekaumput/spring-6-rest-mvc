package com.example.springframework.spring6rest.mvc.spring6restmvc.bootstrap;
/*
 * @Author tmekaumput
 * @Date 8/4/24 3:29 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.BeerRepository;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.CustomerRepository;
import com.example.springframework.spring6rest.mvc.spring6restmvc.services.BeerCsvService;
import com.example.springframework.spring6rest.mvc.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.AfterEach;
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


    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        beerRepository.deleteAll();
        customerRepository.deleteAll();

        dataLoader = new DataLoader(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void loadData() throws Exception {
        dataLoader.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }

    @AfterEach
    void tearDown() {
        beerRepository.deleteAll();
        customerRepository.deleteAll();
    }
}
