package com.dev.msscbeerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandQuantity(UUID beerId);
}
