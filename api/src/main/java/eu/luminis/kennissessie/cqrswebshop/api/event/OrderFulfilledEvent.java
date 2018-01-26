package eu.luminis.kennissessie.cqrswebshop.api.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import eu.luminis.kennissessie.cqrswebshop.api.ProductAmountDto;

public class OrderFulfilledEvent {

    private final List<ProductAmountDto> products;

    public OrderFulfilledEvent(List<ProductAmountDto> products) {
        Assert.isTrue(!products.isEmpty(), "Products cannot be empty");
        this.products = products;
    }

    public List<ProductAmountDto> getProducts() {
        return new ArrayList<>(products);
    }
}
