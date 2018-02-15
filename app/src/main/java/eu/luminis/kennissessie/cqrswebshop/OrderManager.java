package eu.luminis.kennissessie.cqrswebshop;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.springframework.beans.factory.annotation.Autowired;

import eu.luminis.kennissessie.cqrswebshop.api.ProductAmountDto;
import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.event.OrderRequestedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentExpiredEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentSuccessfulEvent;

public class OrderManager {

    @Autowired
    private transient EventBus eventBus;

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient EventScheduler eventScheduler;

    private UUID paymentId;

    private List<ProductAmountDto> productAmountDtos = new ArrayList<>();

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRequestedEvent orderRequested){
        this.paymentId = orderRequested.getPaymentId();

        SagaLifecycle.associateWith("paymentId", this.paymentId.toString());

        commandGateway.send(new CreatePaymentCommand(paymentId, orderRequested.getOrderTotalPrice()));

        // TODO: Implement reserve stock command

        eventScheduler.schedule(Duration.ofMinutes(10), new PaymentExpiredEvent(paymentId));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(PaymentExpiredEvent paymentExpiredEvent){
        // TODO: Implement payment cancellation
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(PaymentSuccessfulEvent paymentSuccessfulEvent){
        // TODO: Emit OrderFulfilledEvent
    }



}
