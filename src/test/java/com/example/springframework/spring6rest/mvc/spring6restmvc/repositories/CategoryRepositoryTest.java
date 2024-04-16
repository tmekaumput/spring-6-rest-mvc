package com.example.springframework.spring6rest.mvc.spring6restmvc.repositories;

import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Category;
import com.example.springframework.spring6rest.mvc.spring6restmvc.entities.Beer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @Author tmekaumput
 * @Date 16/4/24 11:24 am
 *
 */

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;

    Beer beer;

    @BeforeEach
    void setUp() {
        beer = beerRepository.findAll().get(0);
    }

    @Test
    void findAll() {
        List<Category> categories = categoryRepository.findAll();

        System.out.println(categories.size());
    }

    @Test
    @Transactional
    @Rollback
    void createCategory() {
        Category category = Category.builder()
                .description("Test description")
                .build();

        beer.addCategory(category);

        Beer savedBeer = beerRepository.save(beer);
        Category savedCategory = categoryRepository.save(category);

        System.out.println(savedCategory.getId());
        System.out.println(savedCategory.getBeers().size());
        System.out.println(savedBeer.getId());
        System.out.println(savedBeer.getCategories().size());
    }


}