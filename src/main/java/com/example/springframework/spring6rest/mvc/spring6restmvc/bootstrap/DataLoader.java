package com.example.springframework.spring6rest.mvc.spring6restmvc.bootstrap;
/*
 * @Author tmekaumput
 * @Date 8/4/24 3:00 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Customer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.BeerRepository;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    private void loadBeerData() {
        if(beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {
        if(customerRepository.count() == 0) {
            Customer c1 = Customer.builder()
                    .customerName("John Smith")
                    .build();

            Customer c2 = Customer.builder()
                    .customerName("John Woo")
                    .build();

            Customer c3 = Customer.builder()
                    .customerName("John Crap")
                    .build();

            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }
}
