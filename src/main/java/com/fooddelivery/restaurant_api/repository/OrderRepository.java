package com.fooddelivery.restaurant_api.repository;

import com.fooddelivery.restaurant_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByCustomerPhone(String customerPhone);

    List<Order> findByStatus(String status);

    List<Order> findByRestaurantIdAndStatus(Long restaurantId, String status);

    @Query("SELECT o FROM Order o WHERE o.customerPhone = :phone ORDER BY o.createdAt DESC")
    List<Order> findCustomerOrderHistory(@Param("phone") String phone);

    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId " +
            "AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findOrdersByRestaurantAndDateRange(
            @Param("restaurantId") Long restaurantId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.restaurant.id = :restaurantId " +
            "AND o.status = 'DELIVERED' AND o.isPaid = true")
    Double calculateTotalRevenue(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :restaurantId " +
            "AND o.status IN ('PENDING', 'CONFIRMED', 'PREPARING', 'OUT_FOR_DELIVERY')")
    Long countActiveOrders(@Param("restaurantId") Long restaurantId);
}