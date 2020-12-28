package com.dev.msscbeerservice.web.controller;


import com.dev.msscbeerservice.services.BeerService;
import com.dev.msscbeerservice.web.mapper.BeerMapper;
import com.dev.msscbeerservice.web.model.BeerDto;
import com.dev.msscbeerservice.web.model.BeerPagedList;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/v1/beer"))
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE =25 ;
    private final BeerService beerService;
    @GetMapping
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber",required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                                  @RequestParam(value = "beerName",required = false) String beerName,
                                                   @RequestParam(value = "beerStyle",required = false) BeerStyleEnum beerStyle){

if(pageNumber==null || pageNumber<0){
    pageNumber=DEFAULT_PAGE_NUMBER;
}

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        BeerPagedList beerPagedList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(beerPagedList,HttpStatus.OK);
    }


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        BeerDto dto = beerService.getBeerById( beerId);
        return new ResponseEntity<BeerDto>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBeer(@Valid @RequestBody BeerDto beerDto){

        beerService.saveBeer(beerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
        beerService.updateBeer(beerId,beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}