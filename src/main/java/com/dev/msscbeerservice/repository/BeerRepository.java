package com.dev.msscbeerservice.repository;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageRequest);

    Page<Beer> findByBeerName(String beerName, Pageable pageRequest);

    Page<Beer> findByBeerStyle(BeerStyleEnum beerStyle, Pageable pageRequest);

    Beer findByUpc(String upc);

}

