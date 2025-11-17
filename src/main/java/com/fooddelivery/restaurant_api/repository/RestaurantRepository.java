package com.fooddelivery.restaurant_api.repository;

import com.fooddelivery.restaurant_api.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByIsActiveTrue();

    Optional<Restaurant> findByEmail(String email);

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByRatingGreaterThanEqual(Double rating);

    @Query("SELECT r FROM Restaurant r WHERE r.isActive = true AND r.rating >= :minRating")
    List<Restaurant> findActiveRestaurantsWithMinRating(@Param("minRating") Double minRating);
}