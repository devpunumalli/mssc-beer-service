package com.dev.msscbeerservice.services;


import com.dev.msscbeerservice.config.JmsConfig;
import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.event.BeerEvent;
import com.dev.msscbeerservice.repository.BeerRepository;
import com.dev.msscbeerservice.services.inventory.BeerInventoryService;
import com.dev.msscbeerservice.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;

    private JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;
    @Scheduled(fixedRate = 2000)
    public void checkForLowInventory()
    {
        List<Beer> beersList = beerRepository.findAll();

        beersList.forEach(beer->{
            Integer onHandQuantity = beerInventoryService.getOnHandQuantity(beer.getId());

      log.debug("minimum on Hand:"+beer.getMinOnHand());
      log.debug("Inventory on hand is :"+onHandQuantity);

      if(onHandQuantity<=beer.getMinOnHand()){
          //send Beer event
          jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BeerEvent(beerMapper.toBeerDto(beer)));

      }
        });


    }
}
