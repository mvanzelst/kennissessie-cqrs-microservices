package eu.luminis.kennissessie.cqrswebshop.api;

import java.util.UUID;

public class ProductAmountDto {

    private final long amount;

    private final UUID productId;

    public ProductAmountDto(long amount, UUID productId) {
        this.amount = amount;
        this.productId = productId;
    }

    public long getAmount() {
        return amount;
    }

    public UUID getProductId() {
        return productId;
    }
}
