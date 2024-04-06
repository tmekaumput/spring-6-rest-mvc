package com.example.springframework.spring6rest.mvc.spring6restmvc.services;

/*
 * @Author tmekaumput
 * @Date 3/4/24 8:16 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
