package eu.luminis.kennissessie.cqrswebshop.api.command;

import java.util.Objects;
import java.util.UUID;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.springframework.util.Assert;

public class ReserveStockCommand {

    @TargetAggregateIdentifier
    private final UUID productId;
    private final long amount;

    public ReserveStockCommand(UUID productId, long amount) {
        Objects.requireNonNull(productId);
        Assert.isTrue(amount > 0, "amount should be larger than zero");

        this.productId = productId;
        this.amount = amount;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getAmount() {
        return amount;
    }
}
