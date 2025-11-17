package com.fooddelivery.restaurant_api.controller;

import com.fooddelivery.restaurant_api.entity.MenuItem;
import com.fooddelivery.restaurant_api.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        logger.info("GET /menu-items - Fetching all menu items");
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        logger.info("GET /menu-items/{} - Fetching menu item by id", id);
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        logger.info("GET /menu-items/restaurant/{} - Fetching menu items by restaurant", restaurantId);
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurant(restaurantId));
    }

    @GetMapping("/restaurant/{restaurantId}/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(@PathVariable Long restaurantId) {
        logger.info("GET /menu-items/restaurant/{}/available", restaurantId);
        return ResponseEntity.ok(menuItemService.getAvailableMenuItemsByRestaurant(restaurantId));
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<MenuItem> createMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody MenuItem menuItem) {
        logger.info("POST /menu-items/restaurant/{} - Creating new menu item", restaurantId);
        MenuItem created = menuItemService.createMenuItem(restaurantId, menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItem menuItem) {
        logger.info("PUT /menu-items/{} - Updating menu item", id);
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        logger.info("DELETE /menu-items/{} - Deleting menu item", id);
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        logger.info("GET /menu-items/category/{}", category);
        return ResponseEntity.ok(menuItemService.getMenuItemsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(@RequestParam String keyword) {
        logger.info("GET /menu-items/search?keyword={}", keyword);
        return ResponseEntity.ok(menuItemService.searchMenuItems(keyword));
    }
}