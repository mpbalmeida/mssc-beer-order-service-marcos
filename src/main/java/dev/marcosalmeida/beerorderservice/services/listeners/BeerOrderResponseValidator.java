package dev.marcosalmeida.beerorderservice.services.listeners;

import dev.marcosalmeida.brewery.model.events.ValidateOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeerOrderResponseValidator {

    public Boolean validateBeerOrderResponse(ValidateOrderResult validateOrderResult) {
        return validateOrderResult.getIsValid();
    }
}
