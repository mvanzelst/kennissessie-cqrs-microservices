package eu.luminis.kennissessie.cqrswebshop;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Test;

import eu.luminis.kennissessie.cqrswebshop.api.ProductAmountDto;
import eu.luminis.kennissessie.cqrswebshop.api.command.CancelPaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.ReserveStockCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.UndoStockReservationCommand;
import eu.luminis.kennissessie.cqrswebshop.api.event.OrderFulfilledEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.OrderRequestedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentSuccessfulEvent;

public class OrderManagerTest {

    @Test
    public void testPaymentCreationAndStockReservation() {
        UUID productId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();

        int amount = 1;
        List<OrderRequestedEvent.ProductOrderDto> products = Collections.singletonList(new OrderRequestedEvent.ProductOrderDto(amount, productId));
        long orderTotal = 10;


        new SagaTestFixture<>(OrderManager.class)
                .whenPublishingA(new OrderRequestedEvent(orderId, products, orderTotal, paymentId))
                .expectDispatchedCommands(new ReserveStockCommand(productId, amount), new CreatePaymentCommand(paymentId, orderTotal));
    }

    @Test
    public void testPaymentCancellationBecauseOfExpiration() {
        UUID productId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();

        int amount = 1;
        List<OrderRequestedEvent.ProductOrderDto> products = Collections.singletonList(new OrderRequestedEvent.ProductOrderDto(amount, productId));
        long orderTotal = 10;


        new SagaTestFixture<>(OrderManager.class)
                .givenAPublished(new OrderRequestedEvent(orderId, products, orderTotal, paymentId))
                .whenTimeElapses(Duration.ofMinutes(11))
                .expectDispatchedCommands(new CancelPaymentCommand(paymentId), new UndoStockReservationCommand(productId, 1));
    }

    @Test
    public void testOrderFulFilledEvent() {
        UUID productId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();

        int amount = 1;
        List<OrderRequestedEvent.ProductOrderDto> products = Collections.singletonList(new OrderRequestedEvent.ProductOrderDto(amount, productId));
        long orderTotal = 10;


        new SagaTestFixture<>(OrderManager.class)
                .givenAPublished(new OrderRequestedEvent(orderId, products, orderTotal, paymentId))
                .whenPublishingA(new PaymentSuccessfulEvent(paymentId))
                .expectPublishedEvents(new OrderFulfilledEvent(Collections.singletonList(new ProductAmountDto(amount, productId))));
    }

}