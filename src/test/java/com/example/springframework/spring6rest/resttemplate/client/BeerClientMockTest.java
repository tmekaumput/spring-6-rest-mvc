package com.example.springframework.spring6rest.resttemplate.client;
/*
 * @Author tmekaumput
 * @Date 29/4/24 10:29 am
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import com.example.springframework.spring6rest.resttemplate.client.model.BeerDTOPageImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest
public class BeerClientMockTest {

    static final String URL = "http://localhost:8080";

    BeerClient beerClient;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    MockRestServiceServer mockRestServiceServer;

    @Autowired
    ObjectMapper objectMapper;


    @Mock
    RestTemplateBuilder mockRestTemplateBuilder = new RestTemplateBuilder(new MockServerRestTemplateCustomizer());

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);
        beerClient = new BeerClientImpl(mockRestTemplateBuilder);
    }

    @Test
    void createBeer() throws JsonProcessingException {
        BeerDTO newBeer = getBeerDto();
        String payload = objectMapper.writeValueAsString(newBeer);

        URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.BEER_ID_PATH)
                        .build(newBeer.getId());

        mockRestServiceServer.expect(method(HttpMethod.POST))
                .andExpect(requestTo(URL + BeerClientImpl.BEER_PATH))
                .andRespond(withAccepted().location(uri));

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.BEER_ID_PATH, newBeer.getId()))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        BeerDTO newBeerResponse = beerClient.createBeer(newBeer);

        assertThat(newBeerResponse.getId()).isEqualTo(newBeer.getId());
        assertThat(newBeerResponse.getBeerName()).isEqualTo(newBeer.getBeerName());
    }

    @Test
    void updateBeer() throws JsonProcessingException {
        BeerDTO beerDto = getBeerDto();
        String payload = objectMapper.writeValueAsString(beerDto);

        URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.BEER_ID_PATH)
                .build(beerDto.getId());

        mockRestServiceServer.expect(method(HttpMethod.PUT))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.BEER_ID_PATH, beerDto.getId()))
                .andRespond(withNoContent());

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.BEER_ID_PATH, beerDto.getId()))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        BeerDTO updatedBeer = beerClient.updateBeer(beerDto);


        mockRestServiceServer.verify();
        //assertThat(updatedBeerResponse.getId()).isEqualTo(updatedBeer.getId());
    }

    @Test
    void deleteBeer() throws JsonProcessingException {
        BeerDTO beerDto = getBeerDto();

        URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.BEER_ID_PATH)
                .build(beerDto.getId());

        mockRestServiceServer.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.BEER_ID_PATH, beerDto.getId()))
                .andRespond(withNoContent());

        beerClient.deleteBeer(beerDto.getId());


        mockRestServiceServer.verify();
    }

    @Test
    void getBeerById() throws JsonProcessingException {

        BeerDTO existing = getBeerDto();
        String payload = objectMapper.writeValueAsString(existing);

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.BEER_ID_PATH, existing.getId()))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        BeerDTO beer = beerClient.getBeerById(existing.getId());

        assertThat(beer.getId()).isEqualTo(existing.getId());
    }

    @Test
    void listBeers() throws JsonProcessingException {

        String payload = objectMapper.writeValueAsString(getPage());

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestTo(URL + BeerClientImpl.BEER_PATH))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        Page<BeerDTO> beers = beerClient.listBeers();

        assertThat(beers.getSize()).isGreaterThan(0);
    }


    @Test
    void listBeersByStyle() throws JsonProcessingException {

        String response = objectMapper.writeValueAsString(getPage());

        URI uri = UriComponentsBuilder.fromHttpUrl(URL + BeerClientImpl.BEER_PATH)
                        .queryParam("beerStyle", BeerStyle.LAGER)
                                .build().toUri();

        mockRestServiceServer.expect(method(HttpMethod.GET))
                .andExpect(requestTo(uri))
                .andExpect(queryParam("beerStyle", BeerStyle.LAGER.toString()))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        Page<BeerDTO> beers = beerClient.listBeers(null, BeerStyle.LAGER, null, null,null);

        assertThat(beers.getSize()).isGreaterThan(0);
    }


    BeerDTO getBeerDto() {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();
    }

    BeerDTOPageImpl getPage() {
        return new BeerDTOPageImpl(Arrays.asList(getBeerDto()), 1, 25, 1L);
    }
}
