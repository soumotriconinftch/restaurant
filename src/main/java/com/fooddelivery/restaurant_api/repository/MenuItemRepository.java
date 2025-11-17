package com.fooddelivery.restaurant_api.repository;

import com.fooddelivery.restaurant_api.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);

    List<MenuItem> findByCategory(String category);

    List<MenuItem> findByIsVegetarianTrue();

    List<MenuItem> findByIsAvailableTrue();

    List<MenuItem> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);

    List<MenuItem> findByRestaurantIdAndCategory(Long restaurantId, String category);

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId " +
            "AND m.isAvailable = true AND m.price BETWEEN :minPrice AND :maxPrice")
    List<MenuItem> findAvailableItemsByRestaurantAndPriceRange(
            @Param("restaurantId") Long restaurantId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    @Query("SELECT m FROM MenuItem m WHERE m.name LIKE %:keyword% OR m.description LIKE %:keyword%")
    List<MenuItem> searchByKeyword(@Param("keyword") String keyword);
}

// ============================================