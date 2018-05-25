package eu.luminis.kennissessie.cqrswebshop;

import java.util.UUID;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.DepositAmountCommand;
import eu.luminis.kennissessie.cqrswebshop.api.event.AmountDepositedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentCreatedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentSuccessfulEvent;

@RunWith(MockitoJUnitRunner.class)
public class PaymentTest {

    @Mock
    private EventBus eventBusMock;

    private AggregateTestFixture<Payment> testFixture;

    @Before
    public void setup(){
        testFixture = new AggregateTestFixture<>(Payment.class);
    }

    @Test
    public void testPaymentSuccessfulInOneDeposit() {
        UUID paymentId = UUID.randomUUID();
        long orderTotal = 10;

        testFixture.given(new PaymentCreatedEvent(new CreatePaymentCommand(paymentId, orderTotal)))
                   .when(new DepositAmountCommand(paymentId, orderTotal))
                   .expectEvents(new AmountDepositedEvent(paymentId, orderTotal), new PaymentSuccessfulEvent(paymentId));
    }

//    @Test
//    public void testPaymentSuccessfulInMultipleDeposits() {
//        UUID paymentId = UUID.randomUUID();
//        long orderTotal = 10;
//        Payment payment = new Payment(new CreatePaymentCommand(paymentId, orderTotal));
//        injectEventBusMock(payment);
//
//        payment.handle(new DepositAmountCommand(paymentId, 4));
//        payment.handle(new DepositAmountCommand(paymentId, 4));
//        payment.handle(new DepositAmountCommand(paymentId, 2));
//
//        verify(eventBusMock).publish(eventMessageArgumentCaptor.capture());
//
//        assertEquals(new PaymentSuccessfulEvent(paymentId), eventMessageArgumentCaptor.getValue().getPayload());
//    }

//    @Test(expected = IllegalStateException.class)
//    public void testFailureInOverDeposit() {
//        UUID paymentId = UUID.randomUUID();
//        long orderTotal = 10;
//        Payment payment = new Payment(new CreatePaymentCommand(paymentId, orderTotal));
//        injectEventBusMock(payment);
//
//        payment.handle(new DepositAmountCommand(paymentId, 4));
//        payment.handle(new DepositAmountCommand(paymentId, 4));
//        payment.handle(new DepositAmountCommand(paymentId, 5));
//    }
//
//    private void injectEventBusMock(Payment payment) {
//        ReflectionTestUtils.setField(payment, "eventBus", eventBusMock);
//    }

}