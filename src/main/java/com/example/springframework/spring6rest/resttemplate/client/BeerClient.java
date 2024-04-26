package com.example.springframework.spring6rest.resttemplate.client;
/*
 * @Author tmekaumput
 * @Date 23/4/24 9:40 am
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BeerClient {
    BeerDTO createBeer(BeerDTO beerDTO);
    BeerDTO updateBeer(BeerDTO beerDTO);
    BeerDTO deleteBeer(UUID beerId);
    BeerDTO getBeerById(UUID id);
    Page<BeerDTO> listBeers();
    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
}
