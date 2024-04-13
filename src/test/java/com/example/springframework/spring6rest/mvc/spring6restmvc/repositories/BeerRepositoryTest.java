package com.example.springframework.spring6rest.mvc.spring6restmvc.repositories;
/*
 * @Author tmekaumput
 * @Date 6/4/24 6:20 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.bootstrap.DataLoader;
import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import com.example.springframework.spring6rest.mvc.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({DataLoader.class, BeerCsvServiceImpl.class})
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                        .beerStyle(BeerStyle.LAGER)
                        .upc("223432")
                        .price(new BigDecimal("11.00"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void saveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
                    Beer savedBeer = beerRepository.save(Beer.builder()
                            .beerName("My Beer1234567890")
                            .beerStyle(BeerStyle.LAGER)
                            .upc("223432")
                            .price(new BigDecimal("11.00"))
                            .build());

                    beerRepository.flush();
                });
        
    }

    @Test
    void findBeersByName() {
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(336);
    }

    @Test
    void findBeersByStyle() {
        List<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.LAGER);

        assertThat(list.size()).isEqualTo(39);
    }
}
