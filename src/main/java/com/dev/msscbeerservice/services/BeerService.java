package com.dev.msscbeerservice.services;

import com.dev.msscbeerservice.web.model.BeerDto;
import com.dev.msscbeerservice.web.model.BeerPagedList;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerService {
    BeerDto saveBeer(BeerDto beerDto);

    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, Pageable of, Boolean showInventoryOnHand);
}
