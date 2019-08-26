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

    public Order() {
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
        this.currency = "EUR";
    }

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

    public void addOrderItem(OrderItem orderItem) {
        this.items.add(orderItem);
    }

    public void approve(boolean approved) {
        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (approved) {
            changeStatusToApproved();
        } else {
            changeStatusToRejected();
        }

    }

    private void changeStatusToRejected() {
        if (isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
        this.status = OrderStatus.REJECTED;
    }

    private void changeStatusToApproved() {
        if (isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }
        this.status = OrderStatus.APPROVED;
    }

    public void ship() {
        if (isCreated() || isRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }

        this.status = OrderStatus.SHIPPED;
    }

    private void validateForShipping() {
        if (isCreated() || isRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }
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
        return status.equals(CREATED);
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
}
