package com.fooddelivery.restaurant_api.controller;

import com.fooddelivery.restaurant_api.entity.Order;
import com.fooddelivery.restaurant_api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("GET /orders - Fetching all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        logger.info("GET /orders/{} - Fetching order by id", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<Order> getOrderByOrderNumber(@PathVariable String orderNumber) {
        logger.info("GET /orders/order-number/{}", orderNumber);
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        logger.info("GET /orders/restaurant/{}", restaurantId);
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId));
    }

    @GetMapping("/customer/{customerPhone}")
    public ResponseEntity<List<Order>> getCustomerOrderHistory(@PathVariable String customerPhone) {
        logger.info("GET /orders/customer/{}", customerPhone);
        return ResponseEntity.ok(orderService.getCustomerOrderHistory(customerPhone));
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Order> createOrder(
            @PathVariable Long restaurantId,
            @Valid @RequestBody Order order) {
        logger.info("POST /orders/restaurant/{} - Creating new order", restaurantId);
        Order created = orderService.createOrder(restaurantId, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        logger.info("PATCH /orders/{}/status?status={}", id, status);
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        logger.info("DELETE /orders/{}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurant/{restaurantId}/active-count")
    public ResponseEntity<Map<String, Long>> getActiveOrdersCount(@PathVariable Long restaurantId) {
        logger.info("GET /orders/restaurant/{}/active-count", restaurantId);
        Long count = orderService.getActiveOrdersCount(restaurantId);
        Map<String, Long> response = new HashMap<>();
        response.put("activeOrders", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurant/{restaurantId}/revenue")
    public ResponseEntity<Map<String, Double>> calculateRevenue(@PathVariable Long restaurantId) {
        logger.info("GET /orders/restaurant/{}/revenue", restaurantId);
        Double revenue = orderService.calculateRevenue(restaurantId);
        Map<String, Double> response = new HashMap<>();
        response.put("totalRevenue", revenue);
        return ResponseEntity.ok(response);
    }
}