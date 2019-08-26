package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;

import java.util.List;

class SetableOrder extends Order {

    public SetableOrder(List<OrderItem> orderItems) {
        super(orderItems);

    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public void setId(int id) {
        this.id = id;
    }

}
