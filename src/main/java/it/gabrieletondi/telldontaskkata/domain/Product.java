package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Product {
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    private Category category;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getUnitaryTax() {
        return price
                .divide(valueOf(100))
                .multiply(category.getTaxPercentage())
                .setScale(2, HALF_UP);
    }

    public BigDecimal getFinalPrice() {
        return price.add(getUnitaryTax()).setScale(2, HALF_UP);
    }
}
