package com.dev.msscbeerservice.services.inventory;

import com.dev.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class BeerInventoryServiceRestTempImpl implements BeerInventoryService {

    private static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    private RestTemplate restTemplate;


    private String beerInventoryServiceHost;

    @Override
    public Integer getOnHandQuantity(UUID beerId) {

        log.debug("Calling BeerInventory Service");

        ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                                                                                      new ParameterizedTypeReference<List<BeerInventoryDto>>() {
                                                                                      }, (Object) beerId);
        int onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
        ;
        return onHand;
    }

    public BeerInventoryServiceRestTempImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
}
