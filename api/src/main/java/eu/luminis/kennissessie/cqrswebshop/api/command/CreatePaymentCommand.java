package eu.luminis.kennissessie.cqrswebshop.api.command;

import java.util.Objects;
import java.util.UUID;

import org.springframework.util.Assert;

public class CreatePaymentCommand {

    private final UUID paymentId;

    private final long amount;

    public CreatePaymentCommand(UUID paymentId, long amount) {
        Objects.requireNonNull(paymentId);
        Assert.isTrue(amount > 0, "amount should be larger than zero");

        this.paymentId = paymentId;
        this.amount = amount;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public long getAmount() {
        return amount;
    }
}
