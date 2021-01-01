package com.dev.msscbeerservice.services.brewing;

import com.dev.msscbeerservice.config.JmsConfig;
import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.event.BrewBeerEvent;
import com.dev.msscbeerservice.event.InventoryEvent;
import com.dev.msscbeerservice.repository.BeerRepository;
import com.dev.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent){

        BeerDto beerDto = brewBeerEvent.getBeerDto();
        Beer beer = beerRepository.getOne(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        InventoryEvent inventoryEvent=new InventoryEvent(beerDto);


        log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.INVENTORY_REQUEST,inventoryEvent);

    }
}
