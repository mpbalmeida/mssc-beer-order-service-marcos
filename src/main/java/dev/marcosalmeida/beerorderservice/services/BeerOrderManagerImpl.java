package dev.marcosalmeida.beerorderservice.services;

import dev.marcosalmeida.beerorderservice.domain.BeerOrder;
import dev.marcosalmeida.beerorderservice.domain.BeerOrderEventEnum;
import dev.marcosalmeida.beerorderservice.domain.BeerOrderStatusEnum;
import dev.marcosalmeida.beerorderservice.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BeerOrderManagerImpl implements BeerOrderManager {

    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;
    private final BeerOrderRepository beerOrderRepository;

    @Transactional
    @Override
    public BeerOrder newBeerOrder(BeerOrder beerOrder) {
        beerOrder.setId(null);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);

        var saverBeerOrder = beerOrderRepository.save(beerOrder);
        sendBeerOrderEvent(saverBeerOrder, BeerOrderEventEnum.VALIDATE_ORDER);

        return saverBeerOrder;
    }

    private void sendBeerOrderEvent(BeerOrder beerOrder, BeerOrderEventEnum eventEnum) {
        var sm = build(beerOrder);

        var msg = MessageBuilder.withPayload(eventEnum).build();

        sm.sendEvent(msg);
    }

    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder) {
        var sm = stateMachineFactory.getStateMachine(beerOrder.getId());

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> sma.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(), null, null, null)));

        sm.start();

        return sm;
    }
}
