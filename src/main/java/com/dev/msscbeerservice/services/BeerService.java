package com.dev.msscbeerservice.services;

import com.dev.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto saveBeer(BeerDto beerDto);

    BeerDto getBeerById(UUID beerId);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
