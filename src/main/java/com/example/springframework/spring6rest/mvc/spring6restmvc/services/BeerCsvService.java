package com.example.springframework.spring6rest.mvc.spring6restmvc.services;
/*
 * @Author tmekaumput
 * @Date 12/4/24 5:06 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.BeerCSVRecord;

import java.util.List;
import java.io.File;

public interface BeerCsvService {
    List<BeerCSVRecord>  convertCSV(File csvFile);
}
