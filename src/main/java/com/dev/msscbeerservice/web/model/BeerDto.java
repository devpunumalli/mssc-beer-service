package com.dev.msscbeerservice.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ",shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;
@Null
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ",shape = JsonFormat.Shape.STRING)
    private OffsetDateTime lastModifiedDate;

    @NotBlank
    private String beerName;

    @NotNull
    @Positive
    private Integer upc;

    private BeerStyleEnum beerStyle;

 @NotNull
 @Positive
 @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    @NotNull
    private Integer quantityOnHand;


}
