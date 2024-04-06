package com.example.springframework.spring6rest.mvc.spring6restmvc.repositories;
/*
 * @Author tmekaumput
 * @Date 6/4/24 6:06 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
