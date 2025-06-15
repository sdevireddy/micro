package com.orders.orderservice.services;

import com.orders.orderservice.dto.InventoryResponse;
import com.orders.orderservice.dto.OrderItemsRequest;
import com.orders.orderservice.dto.OrderRequest;
import com.orders.orderservice.model.Order;
import com.orders.orderservice.model.OrderItems;
import com.orders.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItemsRequest> orderItems = orderRequest.getOrderItemsRequestList();
        List<OrderItems> orders = orderItems.stream().map(or->createOrderItem(or)).toList();
        order.setOrderItems(orders);
        List<String> skuCodes = orders.stream().map(OrderItems::getSkuCode).toList();
     /*   Order order = Order.builder().orderNumber(UUID.randomUUID().toString())
                .orderItems(orderRequest.getOrderItemsRequestList().stream().map(request->createOrderItem(request)).toList()).build();*/
        // inventory service and place orderif product is on
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory/check",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
       if (allInStock) {
           orderRepository.save(order);
       } else {
           throw new IllegalArgumentException("Product is not in stock, please try again later");
       }

    }
    private OrderItems createOrderItem(OrderItemsRequest request) {
        return OrderItems.builder()
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .skuCode(request.getSkuCode())
                .build();
    }
}
