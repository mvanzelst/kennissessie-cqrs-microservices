package eu.luminis.kennissessie.cqrswebshop;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.CreateProductCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.DepositAmountCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.ReserveStockCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.UndoStockReservationCommand;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentSuccessfulEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.ProductFreedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.ProductReservedEvent;

@RunWith(MockitoJUnitRunner.class)
public class ProductTest {

    @Mock
    private EventBus eventBusMock;

    @Captor
    private ArgumentCaptor<EventMessage<?>> eventMessageArgumentCaptor;

    @Test
    public void testReserveProductSuccess() {
        UUID productId = UUID.randomUUID();
        long stockTotal = 10;
        Product product = new Product(new CreateProductCommand(productId, stockTotal));
        injectEventBusMock(product);

        product.handle(new ReserveStockCommand(productId, 10));

        verify(eventBusMock).publish(eventMessageArgumentCaptor.capture());

        assertEquals(new ProductReservedEvent(productId), eventMessageArgumentCaptor.getValue().getPayload());
    }

    @Test(expected = IllegalStateException.class)
    public void testReserveProductFail() {
        UUID productId = UUID.randomUUID();
        long stockTotal = 10;
        Product product = new Product(new CreateProductCommand(productId, stockTotal));
        injectEventBusMock(product);

        product.handle(new ReserveStockCommand(productId, 11));
    }

    @Test
    public void testUndoReserveProductSuccess() {
        UUID productId = UUID.randomUUID();
        long stockTotal = 10;
        Product product = new Product(new CreateProductCommand(productId, stockTotal));
        injectEventBusMock(product);

        product.handle(new UndoStockReservationCommand(productId, 10));

        verify(eventBusMock).publish(eventMessageArgumentCaptor.capture());

        assertEquals(new ProductFreedEvent(productId), eventMessageArgumentCaptor.getValue().getPayload());
    }

    private void injectEventBusMock(Product product) {
        ReflectionTestUtils.setField(product, "eventBus", eventBusMock);
    }

}