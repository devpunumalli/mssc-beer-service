package com.dev.msscbeerservice.bootstrap;

import com.dev.msscbeerservice.domain.Beer;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import com.dev.msscbeerservice.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final UUID BEER_ID_1 = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_ID_2 = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_ID_3 = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerRepository beerRepository;
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository != null && beerRepository.count()==0) {

            Beer b1 = Beer.builder()
                    .beerName("Mango Bobs").id(BEER_ID_1)
                    .beerStyle(BeerStyleEnum.IPA.name())
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .price(new BigDecimal("12.95"))
                    .upc(BEER_1_UPC)
                    .build();

            Beer b2 = Beer.builder()
                    .beerName("Galaxy Cat").id(BEER_ID_2)
                    .beerStyle(BeerStyleEnum.PALE_ALE.name())
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .price(new BigDecimal("12.95"))
                    .upc(BEER_2_UPC)
                    .build();

            Beer b3 = Beer.builder()
                    .beerName("Pinball Porter").id(BEER_ID_3)
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
