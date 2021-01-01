package com.dev.msscbeerservice.event;

import com.dev.msscbeerservice.web.model.BeerDto;

public class InventoryEvent extends BeerEvent{
    public InventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
