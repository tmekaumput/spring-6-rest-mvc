package com.example.springframework.spring6rest.mvc.spring6restmvc.controller;

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.mappers.BeerMapper;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import com.example.springframework.spring6rest.mvc.spring6restmvc.repositories.BeerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @Author tmekaumput
 * @Date 8/4/24 5:28 pm
 *
 */

@SpringBootTest
@Slf4j
class BeerControllerIntegrationTest {

    private static final int DEFAULT_PAGE_SIZE = 25;

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void listAllBeers() {
        Page<BeerDTO> beers = beerController.listBeers(null, null, false, null, null);

        assertThat(beers.getContent().size()).isEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    void listBeersByName() {
        Page<BeerDTO> beers = beerController.listBeers("IPA", null, false, null, null);

        assertThat(beers.getContent().size()).isEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    void listBeersByNameWithoutInventory() {
        Page<BeerDTO> beers = beerController.listBeers("IPA", null, false, null, null);

        assertThat(beers.getContent().size()).isEqualTo(DEFAULT_PAGE_SIZE);
        assertThat(beers.getContent().get(0).getQuantityOnHand()).isNull();
    }

    @Test
    void listBeersByNameWithInventory() {
        Page<BeerDTO> beers = beerController.listBeers("IPA", null, true, null, null);

        assertThat(beers.getContent().size()).isEqualTo(DEFAULT_PAGE_SIZE);
        assertThat(beers.getContent().get(0).getQuantityOnHand()).isNotNull();
    }


    @Test
    void listBeersByNameWithInventoryPage2() {
        Page<BeerDTO> beers = beerController.listBeers("IPA", null, true, 2, 50);

        assertThat(beers.getContent().size()).isEqualTo(50);
        assertThat(beers.getContent().get(0).getQuantityOnHand()).isNotNull();
    }

    @Test
    void listBeersByStyle() {
        Page<BeerDTO> beers = beerController.listBeers(null, BeerStyle.LAGER, false, 1, 100);

        assertThat(beers.getContent().size()).isEqualTo(39);
    }

    @Test
    void listBeersByNameAndStyle() {
        Page<BeerDTO> beers = beerController.listBeers("Shift", BeerStyle.LAGER, false, null, null);

        assertThat(beers.getContent().size()).isEqualTo(3);
    }

    @Test
    @Rollback
    @Transactional
    void emptyList() {
        beerRepository.deleteAll();

        Page<BeerDTO> beers = beerController.listBeers(null, null, false, null, null);

        assertThat(beers.getContent().size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        BeerDTO beer = beerController.listBeers(null, null, false, null, null).getContent().get(0);

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
                .upc("123456")
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
    void patchBeerTooLongBeerName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        beerMap.put("beerStyle", beerDTO.getBeerStyle());
        beerMap.put("price", beerDTO.getPrice());
        beerMap.put("upc", beerDTO.getUpc());

        MvcResult mvcResult =  mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
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