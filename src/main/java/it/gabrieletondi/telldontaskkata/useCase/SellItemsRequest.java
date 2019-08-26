package it.gabrieletondi.telldontaskkata.useCase;

import java.util.List;

public class SellItemsRequest {
    private final List<SellItemRequest> requests;

    public SellItemsRequest(List<SellItemRequest> requests) {
        this.requests = requests;
    }

    public Iterable<SellItemRequest> getRequests() {
        return requests;
    }
}
