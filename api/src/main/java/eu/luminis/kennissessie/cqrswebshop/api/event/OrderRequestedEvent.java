package eu.luminis.kennissessie.cqrswebshop.api.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.util.Assert;

public class OrderRequestedEvent {

    private final UUID orderId;

    private final List<ProductOrderDto> products;

    private final long orderTotalPrice;

    private final UUID paymentId;

    public OrderRequestedEvent(UUID orderId, List<ProductOrderDto> products, long orderTotalPrice, UUID paymentId) {
        Assert.isTrue(products.size() > 0, "Should order at least one product");
        Assert.isTrue(orderTotalPrice > 0, "Should be larger than zero");
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(paymentId);
        this.orderId = orderId;
        this.products = products;
        this.orderTotalPrice = orderTotalPrice;
        this.paymentId = paymentId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public List<ProductOrderDto> getProductOrderDtos() {
        return new ArrayList<>(products);
    }

    public long getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public static class ProductOrderDto {

        private long amount;
        private UUID productId;

        public ProductOrderDto(long amount, UUID productId) {
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
}
