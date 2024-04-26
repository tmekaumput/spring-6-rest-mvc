package com.example.springframework.spring6rest.resttemplate.client;

/*
 * @Author tmekaumput
 * @Date 23/4/24 9:41 am
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import com.example.springframework.spring6rest.resttemplate.client.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    private static final String BASE_URI = "http://localhost:8080";

    private static final String BEER_PATH = "/api/v1/beer";

    private static final String BEER_ID_PATH = "/api/v1/beer/{beerId}";


    @Override
    public BeerDTO createBeer(BeerDTO beerDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();

//        ResponseEntity<BeerDTO> response = restTemplate.postForEntity(BASE_URI + BEER_PATH, beerDTO, BeerDTO.class);

        URI uri = restTemplate.postForLocation(BASE_URI + BEER_PATH, beerDTO);
        return restTemplate.getForObject(BASE_URI + uri.getPath(), BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID id) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        return restTemplate.getForObject(BASE_URI + BEER_ID_PATH, BeerDTO.class, id);
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(BASE_URI + BEER_PATH);

        if(beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if(beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if(showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", showInventory);
        }

        if(pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", pageNumber);
        }

        if(pageSize != null){
            uriComponentsBuilder.queryParam("pageSize", pageSize);
        }

        ResponseEntity<BeerDTOPageImpl> response = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

        return response.getBody();

    }

}
