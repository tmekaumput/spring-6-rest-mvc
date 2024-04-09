package com.example.springframework.spring6rest.mvc.spring6restmvc.controller;

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.mappers.BeerMapper;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @Author tmekaumput
 * @Date 8/4/24 5:28 pm
 *
 */

@SpringBootTest
class BeerControllerIntegrationTest {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;


    @Test
    void listAllBeers() {
        List<BeerDTO> beers = beerController.listBeers();

        assertThat(beers.size()).isEqualTo(3);
    }

    @Test
    @Rollback
    @Transactional
    void emptyList() {
        beerRepository.deleteAll();

        List<BeerDTO> beers = beerController.listBeers();

        assertThat(beers.size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        BeerDTO beer = beerController.listBeers().get(0);

        assertThat(beer).isNotNull();
    }

    @Test
    void getBeerNotFound() {
        assertThrows(NotFoundException.class, () ->
                beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Rollback
    @Transactional
    void createBeer() {
        String beerName = "New beer";

        BeerDTO beer = BeerDTO.builder()
                .beerName(beerName)
                .beerStyle(BeerStyle.LAGER)
                .upc("")
                .price(new BigDecimal("1.00"))
                .build();

        ResponseEntity response = beerController.handlePost(beer);
        String[] pathElements = response.getHeaders().getLocation().getPath().split("/");
        String uuid = pathElements[pathElements.length-1];

        assertTrue(uuid.matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));

        BeerDTO newBeer = beerController.getBeerById(UUID.fromString(uuid));

        assertThat(newBeer).isNotNull();
        assertThat(newBeer.getBeerName()).isEqualTo(beerName);
    }

    @Test
    @Rollback
    @Transactional
    void updateBeer() {
        Beer beer = beerRepository.findAll().get(0);

        final String beerName = "Updated beer";

        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

                beerDTO.setId(null);
                beerDTO.setVersion(null);
                beerDTO.setBeerName(beerName);

        ResponseEntity response = beerController.updateById(beer.getId(), beerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Test
    void updateBeerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Test
    @Rollback
    @Transactional
    void deleteBeer() {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());


    }

    @Test
    void deleteBeerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }


}