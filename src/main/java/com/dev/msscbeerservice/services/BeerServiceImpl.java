package com.dev.msscbeerservice.services;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.repository.BeerRepository;
import com.dev.msscbeerservice.web.controller.NotFoundException;
import com.dev.msscbeerservice.web.mapper.BeerMapper;
import com.dev.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return beerMapper.toBeerDto(beerRepository.save(beerMapper.toBeer(beerDto)));
    }

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.toBeerDto(beerRepository.findById(beerId).orElseThrow(
                NotFoundException::new));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {

        Beer beer = beerRepository.findById(beerId).orElseThrow(
                NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setPrice(beerDto.getPrice());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        return beerMapper.toBeerDto(beerRepository.save(beer));
    }
}
