package com.example.springframework.spring6rest.resttemplate.client;

import com.example.springframework.spring6rest.mvc.spring6restmvc.controller.NotFoundException;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * @Author tmekaumput
 * @Date 23/4/24 9:45 am
 *
 */

@SpringBootTest
class BeerClientImplTest {

    private static final Integer PAGE_SIZE = 20;

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void listBeers() {

        Page<BeerDTO> beers = beerClient.listBeers();

        assertThat(beers.getSize()).isGreaterThan(0);
    }

    @Test
    void listBeersNoBeerName() {

        Page<BeerDTO> beers = beerClient.listBeers(null, null, null, null, null);

        assertThat(beers.getSize()).isGreaterThan(0);
    }

    @Test
    void listBeersByName() {

        Page<BeerDTO> beers = beerClient.listBeers("ALE", null, null, null, null);

        assertThat(beers.getSize()).isGreaterThan(0);
    }

    @Test
    void listBeersByStyle() {
        Page<BeerDTO> beers = beerClient.listBeers(null, BeerStyle.LAGER, null, null, null);

        assertThat(beers.getSize()).isGreaterThan(0);
    }

    @Test
    void listBeersByNameAndStyle() {
        Page<BeerDTO> beers = beerClient.listBeers("ALE", BeerStyle.LAGER, null, null, null);

        assertThat(beers.getSize()).isGreaterThan(0);
    }

    @Test
    void listBeersWithPageSize() {
        Page<BeerDTO> beers = beerClient.listBeers(null, null, null, 0, PAGE_SIZE);

        assertThat(beers.getSize()).isEqualTo(PAGE_SIZE);
    }

    @Test
    void getBeerById() {
        BeerDTO existing = beerClient.listBeers().getContent().get(0);


        BeerDTO beer = beerClient.getBeerById(existing.getId());

        assertThat(beer).isNotNull();
        assertThat(existing.getId()).isEqualTo(beer.getId());

    }

    @Test
    void getBeerByIdNotFound() {

        assertThrows(HttpClientErrorException.class, () -> beerClient.getBeerById(UUID.randomUUID()));


    }
}