package com.dev.msscbeerservice.web.controller;

import com.dev.msscbeerservice.web.model.BeerDto;
import com.dev.msscbeerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.UUID;

import static org.springframework.restdocs.snippet.Attributes.key;
import static org.junit.jupiter.api.Assertions.*;
//important to load rest documentation request builderss
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    BeerDto validBeer;
    @BeforeEach
    public void setUp() {
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.LAGER)
                .upc(134)
                .build();
    }

    @Test
    void getBeerById() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        mockMvc.perform(get("/api/v1/beer/{beerId}",
                            validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(document("v1/beer-get",
                                                           pathParameters (
                                                                   parameterWithName("beerId").description("UUID of desired beer to get.")
                                                           ),
                                                           responseFields(
                                                                   fields.withPath("id").description("Id of Beer").type(UUID.class),
                                                                   fields.withPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                                                   fields.withPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
                                                                   fields.withPath("beerName").description("Beer Name"),
                                                                   fields.withPath("beerStyle").description("Beer Style"),
                                                                   fields.withPath("upc").description("UPC of Beer"),
                                                                   fields.withPath("version").description("version of app"),
                                                                   fields.withPath("price").description("price of Beer"),
                                                                   fields.withPath("quantityOnHand").description("quantity of Beer")

                                                           )));;
    }

    @Test
    void saveBeer() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        BeerDto beerDto = BeerDto.builder().beerName("dev").beerStyle(BeerStyleEnum.LAGER).price(new BigDecimal(123.00)).upc(123).quantityOnHand(1).build();
        String jsonObjStr = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(post("/api/v1/beer/")
                                .content(jsonObjStr)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andDo(document("v1/beer-new",
                                                                requestFields(
                                                                        fields.withPath("id").description("Id of Beer").ignored(),
                                                                        fields.withPath("createdDate").description("Date Created").ignored(),
                                                                        fields.withPath("lastModifiedDate").description("Date Updated").ignored(),
                                                                        fields.withPath("beerName").description("Beer Name"),
                                                                        fields.withPath("beerStyle").description("Beer Style"),
                                                                        fields.withPath("upc").description("UPC of Beer"),
                                                                        fields.withPath("version").description("version of app"),
                                                                        fields.withPath("price").description("price of Beer"),
                                                                        fields.withPath("quantityOnHand").description("quantity of Beer")

                                                                )));;;

    }

    @Test
    void updateBeer() throws Exception {    //given

        BeerDto beerDto = BeerDto.builder().beerName("dev").beerStyle(BeerStyleEnum.LAGER).price(new BigDecimal(123.00)).upc(123).quantityOnHand(1).build();

        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        //when
        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(beerDtoJson))
                .andExpect(status().isNoContent());


    }
    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                                                                                   .collectionToDelimitedString(this.constraintDescriptions
                                                                                                                        .descriptionsForProperty(path), ". ")));
        }
    }
}