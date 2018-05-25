package eu.luminis.kennissessie.cqrswebshop.api.event;

import java.util.UUID;

import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;

public class PaymentCreatedEvent {

    private UUID paymentId;
    private long amount;

    public PaymentCreatedEvent(CreatePaymentCommand createPaymentCommand) {
        paymentId = createPaymentCommand.getPaymentId();
        amount = createPaymentCommand.getAmount();
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public long getAmount() {
        return amount;
    }

}
