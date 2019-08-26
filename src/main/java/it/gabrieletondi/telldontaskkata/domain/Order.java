package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.domain.exceptions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.*;

public class Order {
    private String currency;
    private List<OrderItem> items;
    private OrderStatus status;
    private int id;

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0");

        for (OrderItem item : items) {
            total = total.add(item.getTotalPriceWithTaxes());
        }
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        BigDecimal total = new BigDecimal("0");

        for (OrderItem item : items) {
            total = total.add(item.getTotalTaxes());
        }
        return total;
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
    }

    public void addOrderItem(OrderItem orderItem) {
        this.items.add(orderItem);
    }

    public void approve(boolean approved) {
        validateForApproval(approved);

        this.status = approved ? OrderStatus.APPROVED : OrderStatus.REJECTED;
    }

    public void ship() {
        validateForShipping();

        this.status = OrderStatus.SHIPPED;
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

    private boolean isCreated() {
        return getStatus().equals(CREATED);
    }

    private void validateForApproval(boolean approved) {
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

    private void validateForShipping() {
        if (isCreated() || isRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }
    }
}
