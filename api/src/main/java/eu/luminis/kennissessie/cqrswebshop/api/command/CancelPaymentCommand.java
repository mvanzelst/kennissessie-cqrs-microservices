package eu.luminis.kennissessie.cqrswebshop.api.command;

import java.util.Objects;
import java.util.UUID;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    private final UUID paymentId;

    public CancelPaymentCommand(UUID paymentId) {
        Objects.requireNonNull(paymentId);
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

}
