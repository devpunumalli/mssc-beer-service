package com.dev.msscbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author devpu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BeerDto {
@Null
    private UUID id;

@Null
    private Integer version;
@Null
    private OffsetDateTime createdDate;
@Null
    private OffsetDateTime lastModifiedDate;

    @NotBlank
    private String beerName;

    @NotNull
    @Positive
    private Integer upc;
    @NotBlank
    private BeerStyleEnum beerStyle;

    @NotBlank
    private BigDecimal price;
    @NotBlank
    private Integer quantityOnHand;


}
