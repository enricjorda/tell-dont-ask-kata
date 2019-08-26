package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class OrderItem {
    private final Product product;
    private final int quantity;

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getTotalPriceWithTaxes() {
        return product.getFinalPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
    }

    public BigDecimal getTotalTaxes() {
        return product.getUnitaryTax().multiply(BigDecimal.valueOf(quantity));
    }
}
