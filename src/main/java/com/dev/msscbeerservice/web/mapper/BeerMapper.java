package com.dev.msscbeerservice.web.mapper;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    public Beer toBeer(BeerDto beerDto);
    public BeerDto toBeerDto(Beer beer);
}
