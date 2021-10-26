package dev.marcosalmeida.beerorderservice.services;

import dev.marcosalmeida.beerorderservice.domain.BeerOrder;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
