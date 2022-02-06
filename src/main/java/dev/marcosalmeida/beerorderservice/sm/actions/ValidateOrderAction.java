package dev.marcosalmeida.beerorderservice.sm.actions;

import dev.marcosalmeida.beerorderservice.config.JmsConfig;
import dev.marcosalmeida.beerorderservice.domain.BeerOrderEventEnum;
import dev.marcosalmeida.beerorderservice.domain.BeerOrderStatusEnum;
import dev.marcosalmeida.beerorderservice.repositories.BeerOrderRepository;
import dev.marcosalmeida.beerorderservice.services.BeerOrderManagerImpl;
import dev.marcosalmeida.beerorderservice.web.mapper.BeerOrderMapper;
import dev.marcosalmeida.brewery.model.events.ValidateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {

        var beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        var beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
                .build());

        log.debug("Sent validation requestion to queue for order id {}", beerOrderId);
    }
}
