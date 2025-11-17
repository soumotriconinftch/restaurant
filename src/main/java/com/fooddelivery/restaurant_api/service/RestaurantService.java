package com.fooddelivery.restaurant_api.service;

import com.fooddelivery.restaurant_api.dto.RestaurantRequest;
import com.fooddelivery.restaurant_api.entity.Restaurant;
import com.fooddelivery.restaurant_api.exception.ResourceNotFoundException;
import com.fooddelivery.restaurant_api.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(RestaurantRequest request) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setEmail(request.getEmail());
        restaurant.setAddress(request.getAddress());
        restaurant.setRating(request.getRating());
        //restaurant.setActive(request.isActive());

        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurants() {
        logger.info("Fetching all restaurants");
        return restaurantRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Restaurant> getActiveRestaurants() {
        logger.info("Fetching active restaurants");
        return restaurantRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(Long id) {
        logger.info("Fetching restaurant with id: {}", id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        logger.info("Creating new restaurant: {}", restaurant.getName());
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        logger.info("Updating restaurant with id: {}", id);
        Restaurant restaurant = getRestaurantById(id);

        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhoneNumber(restaurantDetails.getPhoneNumber());
        restaurant.setEmail(restaurantDetails.getEmail());
        restaurant.setDescription(restaurantDetails.getDescription());
        restaurant.setRating(restaurantDetails.getRating());
        restaurant.setIsActive(restaurantDetails.getIsActive());

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        logger.info("Deleting restaurant with id: {}", id);
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> searchRestaurants(String name) {
        logger.info("Searching restaurants by name: {}", name);
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurantsByMinRating(Double rating) {
        logger.info("Fetching restaurants with rating >= {}", rating);
        return restaurantRepository.findByRatingGreaterThanEqual(rating);
    }
}
