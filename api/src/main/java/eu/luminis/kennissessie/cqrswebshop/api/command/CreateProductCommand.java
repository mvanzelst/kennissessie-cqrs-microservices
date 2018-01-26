package eu.luminis.kennissessie.cqrswebshop.api.command;

import java.util.Objects;
import java.util.UUID;

import org.springframework.util.Assert;

public class CreateProductCommand {

    private final UUID productId;

    private final long initialAmountInStock;

    public CreateProductCommand(UUID productId, long initialAmountInStock) {
        Objects.requireNonNull(productId);
        Assert.isTrue(initialAmountInStock > 0, "InitialAmountInStock should be larger than zero");
        this.productId = productId;
        this.initialAmountInStock = initialAmountInStock;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getInitialAmountInStock() {
        return initialAmountInStock;
    }
}
