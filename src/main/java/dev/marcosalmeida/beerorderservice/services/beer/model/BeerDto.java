package dev.marcosalmeida.beerorderservice.services.beer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BeerDto {
    private UUID id;
    private String upc;
    private String beerName;
    private Integer orderQuantity;
    private String beerStyle;
    private BigDecimal price;
}
