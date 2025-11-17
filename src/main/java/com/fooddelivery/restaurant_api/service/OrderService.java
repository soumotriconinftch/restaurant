package com.fooddelivery.restaurant_api.service;

import com.fooddelivery.restaurant_api.entity.Order;
import com.fooddelivery.restaurant_api.entity.Restaurant;
import com.fooddelivery.restaurant_api.exception.ResourceNotFoundException;
import com.fooddelivery.restaurant_api.repository.OrderRepository;
import com.fooddelivery.restaurant_api.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        logger.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Order getOrderByOrderNumber(String orderNumber) {
        logger.info("Fetching order with order number: {}", orderNumber);
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with order number: " + orderNumber));
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        logger.info("Fetching orders for restaurant id: {}", restaurantId);
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<Order> getCustomerOrderHistory(String customerPhone) {
        logger.info("Fetching order history for customer: {}", customerPhone);
        return orderRepository.findCustomerOrderHistory(customerPhone);
    }

    @Transactional
    public Order createOrder(Long restaurantId, Order order) {
        logger.info("Creating new order for restaurant id: {}", restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        order.setRestaurant(restaurant);
        order.setOrderNumber(generateOrderNumber());
        order.setStatus("PENDING");

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        logger.info("Updating order {} status to: {}", id, status);
        Order order = getOrderById(id);
        order.setStatus(status);

        if ("DELIVERED".equals(status)) {
            order.setDeliveredAt(LocalDateTime.now());
        }

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        logger.info("Deleting order with id: {}", id);
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public Long getActiveOrdersCount(Long restaurantId) {
        logger.info("Counting active orders for restaurant id: {}", restaurantId);
        return orderRepository.countActiveOrders(restaurantId);
    }

    @Transactional(readOnly = true)
    public Double calculateRevenue(Long restaurantId) {
        logger.info("Calculating revenue for restaurant id: {}", restaurantId);
        Double revenue = orderRepository.calculateTotalRevenue(restaurantId);
        return revenue != null ? revenue : 0.0;
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}