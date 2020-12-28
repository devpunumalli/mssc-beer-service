package com.dev.msscbeerservice.web.mapper;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.services.inventory.BeerInventoryService;
import com.dev.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper{

    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public Beer toBeer(BeerDto beerDto){
        return beerMapper.toBeer(beerDto);
    }
    @Override
    public BeerDto toBeerDto(Beer beer){
        BeerDto beerDto=beerMapper.toBeerDto(beer);
               beerDto.setQuantityOnHand( beerInventoryService.getOnHandQuantity(beerDto.getId()));
               return  beerDto;
    }


}
