package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.useCase.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.useCase.ShippedOrdersCannotBeChangedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order() {
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
        this.currency = "EUR";
        this.total = new BigDecimal("0.00");
        this.tax = new BigDecimal("0.00");
    }

    public void addOrderItem(OrderItem orderItem) {
        this.items.add(orderItem);
        this.total = total.add(orderItem.getTotalPriceWithTaxes());
        this.tax = tax.add(orderItem.getTotalTaxes());
    }

    private boolean isShipped() {
        return status.equals(OrderStatus.SHIPPED);
    }

    private boolean isRejected() {
        return status.equals(OrderStatus.REJECTED);
    }

    private boolean isApproved() {
        return status.equals(OrderStatus.APPROVED);
    }

    public void approve(boolean approved) {
        validationsForChangeStatus(approved);
        this.status = approved ? OrderStatus.APPROVED : OrderStatus.REJECTED;
    }

    private void validationsForChangeStatus(boolean approved) {
        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (approved && isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!approved && isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
    }
}
