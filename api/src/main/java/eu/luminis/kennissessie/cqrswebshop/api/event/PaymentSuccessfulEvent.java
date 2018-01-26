package eu.luminis.kennissessie.cqrswebshop.api.event;

import java.util.Objects;
import java.util.UUID;

public class PaymentSuccessfulEvent {

    private final UUID paymentId;

    public PaymentSuccessfulEvent(UUID paymentId) {
        Objects.requireNonNull(paymentId);
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }
}
