package com.dev.msscbeerservice.event;

import com.dev.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Data
@Builder
public class BeerEvent implements Serializable
{

    static final long serialVersionUID= -9020656274974724762L;

    private final BeerDto beerDto;

}
