package com.fooddelivery.restaurant_api.dto;

import lombok.Data;

@Data
public class RestaurantRequest {
    private String name;
    private String email;
    private String address;
    private Double rating;
    private boolean isActive;
}
