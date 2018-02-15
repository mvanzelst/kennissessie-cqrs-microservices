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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PaymentSuccessfulEvent that = (PaymentSuccessfulEvent) o;
        return Objects.equals(paymentId, that.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }
}
