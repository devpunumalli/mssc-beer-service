package com.dev.msscbeerservice.services;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.repository.BeerRepository;
import com.dev.msscbeerservice.web.controller.NotFoundException;
import com.dev.msscbeerservice.web.mapper.BeerMapper;
import com.dev.msscbeerservice.web.model.BeerDto;
import com.dev.msscbeerservice.web.model.BeerPagedList;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

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
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {

        Beer beer = beerRepository.findById(beerId).orElseThrow(
                NotFoundException::new);
        if (showInventoryOnHand) {

            return beerMapper.toBeerDtoWithInventoryOnHand(beer);
        } else {
            return beerMapper.toBeerDto(beer);
        }

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

    @Override
    @Cacheable(cacheNames = "beerListCache" ,condition = "#showInventoryOnHand==false")

    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, Pageable pageRequest, Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beersPage;
        System.out.println("I am called again" );

        if(showInventoryOnHand==null){
            showInventoryOnHand=false;
        }

        if (StringUtils.hasText(beerName) && beerStyle != null && StringUtils.hasText(beerStyle.name())) {
            beersPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName)) {
            beersPage = beerRepository.findByBeerStyle(beerStyle, pageRequest);
        } else if (beerStyle != null && StringUtils.hasText(beerStyle.name())) {
            beersPage = beerRepository.findByBeerName(beerName, pageRequest);
        } else {
            beersPage = beerRepository.findAll(pageRequest);
        }
if(showInventoryOnHand){
    beerPagedList = new BeerPagedList(beersPage.stream().map(beerMapper::toBeerDtoWithInventoryOnHand).collect(Collectors.toList()),
                                      PageRequest.of(beersPage.getPageable()
                                                             .getPageNumber(), beersPage.getPageable()
                                                             .getPageSize()),
                                      beersPage.getTotalElements());

}else {
    beerPagedList = new BeerPagedList(beersPage.stream().map(beerMapper::toBeerDto).collect(Collectors.toList()),
                                      PageRequest.of(beersPage.getPageable()
                                                             .getPageNumber(), beersPage.getPageable()
                                                             .getPageSize()),
                                      beersPage.getTotalElements());
}
        return beerPagedList;
    }
}
