package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.domain.exceptions.*;

public enum OrderStatus {
    APPROVED {
        @Override
        public OrderStatus ship() {
            return SHIPPED;
        }
        @Override
        public OrderStatus approve() {
            return APPROVED;
        }
        @Override
        public OrderStatus reject() {
            throw new ApprovedOrderCannotBeRejectedException();
        }

    },
    REJECTED {
        @Override
        public OrderStatus ship() {
            throw new OrderCannotBeShippedException();
        }
        @Override
        public OrderStatus approve() {
            throw new RejectedOrderCannotBeApprovedException();
        }
        @Override
        public OrderStatus reject() {
            return REJECTED;
        }
    },
    SHIPPED{
        @Override
        public OrderStatus ship() {
            throw new OrderCannotBeShippedTwiceException();
        }
        @Override
        public OrderStatus approve() {
            throw new ShippedOrdersCannotBeChangedException();
        }
        @Override
        public OrderStatus reject() {
            throw new ShippedOrdersCannotBeChangedException();
        }
    },
    CREATED{
        @Override
        public OrderStatus ship() {
            throw new OrderCannotBeShippedException();
        }
        @Override
        public OrderStatus approve() {
            return APPROVED;
        }
        @Override
        public OrderStatus reject() {
            return REJECTED;
        }
    };

    public abstract OrderStatus ship();
    public abstract OrderStatus approve();
    public abstract OrderStatus reject();
}
