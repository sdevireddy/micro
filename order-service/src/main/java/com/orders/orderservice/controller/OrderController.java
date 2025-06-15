package com.orders.orderservice.controller;

import com.orders.orderservice.dto.OrderRequest;
import com.orders.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

   @PostMapping("/api/create")
    @ResponseStatus(HttpStatus.CREATED)
   @CircuitBreaker(name = "CircuitBreakerService")
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    @GetMapping("/api/all")
    public String getRequest() {
        return "Get Request";
    }
}
