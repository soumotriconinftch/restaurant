
// ============================================

package com.fooddelivery.restaurant_api.service;

import com.fooddelivery.restaurant_api.entity.MenuItem;
import com.fooddelivery.restaurant_api.entity.Restaurant;
import com.fooddelivery.restaurant_api.exception.ResourceNotFoundException;
import com.fooddelivery.restaurant_api.repository.MenuItemRepository;
import com.fooddelivery.restaurant_api.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public List<MenuItem> getAllMenuItems() {
        logger.info("Fetching all menu items");
        return menuItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MenuItem getMenuItemById(Long id) {
        logger.info("Fetching menu item with id: {}", id);
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        logger.info("Fetching menu items for restaurant id: {}", restaurantId);
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId) {
        logger.info("Fetching available menu items for restaurant id: {}", restaurantId);
        return menuItemRepository.findByRestaurantIdAndIsAvailableTrue(restaurantId);
    }

    @Transactional
    public MenuItem createMenuItem(Long restaurantId, MenuItem menuItem) {
        logger.info("Creating new menu item: {} for restaurant id: {}", menuItem.getName(), restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        logger.info("Updating menu item with id: {}", id);
        MenuItem menuItem = getMenuItemById(id);

        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setCategory(menuItemDetails.getCategory());
        menuItem.setIsVegetarian(menuItemDetails.getIsVegetarian());
        menuItem.setIsAvailable(menuItemDetails.getIsAvailable());
        menuItem.setImageUrl(menuItemDetails.getImageUrl());
        menuItem.setPreparationTimeMinutes(menuItemDetails.getPreparationTimeMinutes());

        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        logger.info("Deleting menu item with id: {}", id);
        MenuItem menuItem = getMenuItemById(id);
        menuItemRepository.delete(menuItem);
    }

    @Transactional(readOnly = true)
    public List<MenuItem> getMenuItemsByCategory(String category) {
        logger.info("Fetching menu items by category: {}", category);
        return menuItemRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<MenuItem> searchMenuItems(String keyword) {
        logger.info("Searching menu items with keyword: {}", keyword);
        return menuItemRepository.searchByKeyword(keyword);
    }
}