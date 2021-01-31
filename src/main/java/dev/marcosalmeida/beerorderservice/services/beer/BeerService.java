package dev.marcosalmeida.beerorderservice.services.beer;

import dev.marcosalmeida.beerorderservice.services.beer.model.BeerDto;

import java.util.Optional;

public interface BeerService {
    Optional<BeerDto> getBeerById(String uuid);

    Optional<BeerDto> getBeerByUpc(String upc);
}
