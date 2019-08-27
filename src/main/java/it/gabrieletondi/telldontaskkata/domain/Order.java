package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.domain.exceptions.*;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private String currency;
    private List<OrderItem> items;
    protected OrderStatus status;
    protected int id;

    public Order(List<OrderItem> orderItems) {
        this.status = OrderStatus.CREATED;
        this.items = orderItems;
        this.currency = "EUR";
    }

    public Boolean isId(int checkId) {
        return id==checkId;
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0");

        for (OrderItem item : items) {
            total = total.add(item.getTotalPriceWithTaxes());
        }
        return total;
    }

    public BigDecimal getTax() {
        BigDecimal total = new BigDecimal("0");

        for (OrderItem item : items) {
            total = total.add(item.getTotalTaxes());
        }
        return total;
    }

    public void approve(boolean approved) {
        if (approved) {
            this.status = this.status.approve();
        } else {
            this.status = this.status.reject();
        }
    }

    public void ship() {
        this.status = this.status.ship();
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (currency != null ? !currency.equals(order.currency) : order.currency != null) return false;
        if (items != null ? !items.equals(order.items) : order.items != null) return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "currency='" + currency + '\'' +
                ", items=" + items +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
