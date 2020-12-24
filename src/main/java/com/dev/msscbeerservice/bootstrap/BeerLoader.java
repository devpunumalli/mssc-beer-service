package com.dev.msscbeerservice.bootstrap;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import com.dev.msscbeerservice.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
if(beerRepository==null){

    Beer b1 = Beer.builder()
            .beerName("Mango Bobs")
            .beerStyle(BeerStyleEnum.IPA.name())
            .minOnHand(12)
            .quantityToBrew(200)
            .price(new BigDecimal("12.95"))
            .upc(BEER_1_UPC)
            .build();

    Beer b2 = Beer.builder()
            .beerName("Galaxy Cat")
            .beerStyle(BeerStyleEnum.PALE_ALE.name())
            .minOnHand(12)
            .quantityToBrew(200)
            .price(new BigDecimal("12.95"))
            .upc(BEER_2_UPC)
            .build();

    Beer b3 = Beer.builder()
            .beerName("Pinball Porter")
            .beerStyle(BeerStyleEnum.PALE_ALE.name())
            .minOnHand(12)
            .quantityToBrew(200)
            .price(new BigDecimal("12.95"))
            .upc(BEER_3_UPC)
            .build();

    beerRepository.save(b1);
    beerRepository.save(b2);
    beerRepository.save(b3);
}

System.out.println("beers saved");
    }
}
