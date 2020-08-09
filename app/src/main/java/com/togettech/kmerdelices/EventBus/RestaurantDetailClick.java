package com.togettech.kmerdelices.EventBus;

import com.togettech.kmerdelices.Model.FoodModel;
import com.togettech.kmerdelices.Model.RestaurantModel;

public class RestaurantDetailClick {
    private boolean success;
    private RestaurantModel restaurantModel;

    public RestaurantDetailClick(boolean success, RestaurantModel restaurantModel) {
        this.success = success;
        this.restaurantModel = restaurantModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
