package com.dev.msscbeerservice.services;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.repository.BeerRepository;
import com.dev.msscbeerservice.web.controller.NotFoundException;
import com.dev.msscbeerservice.web.mapper.BeerMapper;
import com.dev.msscbeerservice.web.model.BeerDto;
import com.dev.msscbeerservice.web.model.BeerPagedList;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, Pageable pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> beersPage;

        if(StringUtils.hasText(beerName) && beerStyle!=null && StringUtils.hasText(beerStyle.name())){
            beersPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);}
        else if(StringUtils.hasText(beerName)){
            beersPage=beerRepository.findByBeerStyle(beerStyle,pageRequest);
        }
        else if(beerStyle!=null && StringUtils.hasText(beerStyle.name())){
            beersPage=   beerRepository.findByBeerName(beerName,pageRequest);
        }
        else{
            beersPage= beerRepository.findAll(pageRequest);
        }

        beerPagedList=new BeerPagedList(beersPage.stream().map(beerMapper::toBeerDto).collect(Collectors.toList()),
                                        PageRequest.of(beersPage.getPageable().getPageNumber(),beersPage.getPageable().getPageSize()),
                                        beersPage.getTotalElements());
        return beerPagedList;
    }
}
