package com.example.springframework.spring6rest.mvc.spring6restmvc.repositories;
/*
 * @Author tmekaumput
 * @Date 6/4/24 6:06 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "beer", collectionResourceRel = "beers")
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String s, BeerStyle beerStyle, Pageable pageable);
}
