package com.example.springframework.spring6rest.resttemplate.client.model;
/*
 * @Author tmekaumput
 * @Date 23/4/24 11:05 am
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = "pageable")
public class BeerDTOPageImpl extends PageImpl<BeerDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerDTOPageImpl(@JsonProperty("content") List<BeerDTO> content,
                           @JsonProperty("number") int number,
                           @JsonProperty("size") int size,
                           @JsonProperty("totalElements") Long totalElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public BeerDTOPageImpl(List<BeerDTO> beers, Pageable pageable, long total) {
        super(beers, pageable, total);
    }

    public BeerDTOPageImpl(List<BeerDTO> beers) {
        super(beers);
    }
}
