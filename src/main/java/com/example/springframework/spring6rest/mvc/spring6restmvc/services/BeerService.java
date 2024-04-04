package com.example.springframework.spring6rest.mvc.spring6restmvc.services;

/*
 * @Author tmekaumput
 * @Date 3/4/24 8:16 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);
}
