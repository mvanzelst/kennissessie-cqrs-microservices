package eu.luminis.kennissessie.cqrswebshop;

import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Test;

import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.DepositAmountCommand;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentSuccessfulEvent;

public class PaymentTest {

    @Test
    public void testPaymentSuccessfulInOneDeposit() {
        UUID paymentId = UUID.randomUUID();
        long orderTotal = 10;


        new AggregateTestFixture<>(Payment.class)
                .givenCommands(new CreatePaymentCommand(paymentId, orderTotal))
                .when(new DepositAmountCommand(paymentId, 10))
                .expectEvents(new PaymentSuccessfulEvent(paymentId));
    }

    @Test
    public void testPaymentSuccessfulInMultipleDeposits() {
        UUID paymentId = UUID.randomUUID();
        long orderTotal = 10;


        new AggregateTestFixture<>(Payment.class)
                .givenCommands(new CreatePaymentCommand(paymentId, orderTotal), new DepositAmountCommand(paymentId, 4), new DepositAmountCommand(paymentId, 4))
                .when(new DepositAmountCommand(paymentId, 2))
                .expectEvents(new PaymentSuccessfulEvent(paymentId));
    }

    @Test
    public void testFailureInOverDeposit() {
        UUID paymentId = UUID.randomUUID();
        long orderTotal = 10;


        new AggregateTestFixture<>(Payment.class)
                .givenCommands(new CreatePaymentCommand(paymentId, orderTotal), new DepositAmountCommand(paymentId, 4), new DepositAmountCommand(paymentId, 4))
                .when(new DepositAmountCommand(paymentId, 5))
                .expectException(IllegalStateException.class);
    }

}