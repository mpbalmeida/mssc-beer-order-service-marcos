package dev.marcosalmeida.beerorderservice.services.listeners;

import dev.marcosalmeida.beerorderservice.config.JmsConfig;
import dev.marcosalmeida.beerorderservice.services.BeerOrderManager;
import dev.marcosalmeida.brewery.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderResponseValidationListener {

    private final BeerOrderResponseValidator validator;
    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result) {
        final var beerOrderId = result.getOrderId();

        log.debug("Validation result for order id: {}", beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, validator.validateBeerOrderResponse(result));
    }

}
