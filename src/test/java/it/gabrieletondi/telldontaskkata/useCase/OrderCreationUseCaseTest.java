package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Category;
import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.domain.exceptions.UnknownProductException;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import it.gabrieletondi.telldontaskkata.useCase.request.SellItemRequest;
import it.gabrieletondi.telldontaskkata.useCase.request.SellItemsRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category() {{
        setName("food");
        setTaxPercentage(new BigDecimal("10"));
    }};
    ;
    private final ProductCatalog productCatalog = new InMemoryProductCatalog(
            Arrays.asList(
                    new Product("salad",new BigDecimal("3.56"), food),
                    new Product("tomato",new BigDecimal("4.65"),food)
            )
    );
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() {

        final SellItemsRequest request = new SellItemsRequest(
                Arrays.asList(
                        new SellItemRequest(2, "salad"),
                        new SellItemRequest(3, "tomato")
                )
        );

        useCase.run(request);


        Order orderToCompare = createOrderToCompare();
        final Order insertedOrder = orderRepository.getSavedOrder();
        assertEquals(orderToCompare, insertedOrder);
        assertThat(insertedOrder.getTotal(), is(new BigDecimal("23.20")));
        assertThat(insertedOrder.getTax(), is(new BigDecimal("2.13")));
    }

    private Order createOrderToCompare() {
        List<OrderItem> orderItems = new ArrayList<>(
                Arrays.asList(
                    new OrderItem(this.productCatalog.getByName("salad"), 2),
                    new OrderItem(this.productCatalog.getByName("tomato"), 3)
                )
        );
        return new Order(orderItems);
    }

    @Test(expected = UnknownProductException.class)
    public void unknownProduct() {
        SellItemRequest unknownProductRequest = new SellItemRequest(0, "unknown product");

        SellItemsRequest request = new SellItemsRequest(new ArrayList() {
            {
                add(unknownProductRequest);
            }
        });

        useCase.run(request);
    }
}
