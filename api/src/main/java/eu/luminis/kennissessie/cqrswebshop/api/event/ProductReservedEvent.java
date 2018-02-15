package eu.luminis.kennissessie.cqrswebshop.api.event;

import java.util.Objects;
import java.util.UUID;

public class ProductReservedEvent {

    private final UUID productId;

    public ProductReservedEvent(UUID productId) {
        Objects.requireNonNull(productId);
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductReservedEvent that = (ProductReservedEvent) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
