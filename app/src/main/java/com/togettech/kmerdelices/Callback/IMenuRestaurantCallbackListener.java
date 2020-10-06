package com.togettech.kmerdelices.Callback;

import com.togettech.kmerdelices.Model.RestaurantModel;

import java.util.List;

public interface IMenuRestaurantCallbackListener {
    void onMenuRestaurantLoadSuccess(List<RestaurantModel> menuRestaurantModels);
    void onMenuRestaurantLoadFailed(String message);
}
