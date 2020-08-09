package com.togettech.kmerdelices.ui.restaurant;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.togettech.kmerdelices.Callback.IRestaurantCallbackListener;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViewModel extends ViewModel implements IRestaurantCallbackListener {

    private MutableLiveData<List<RestaurantModel>> restaurantList;
    private MutableLiveData<String> messageError;
    private IRestaurantCallbackListener restaurantCallbackListener;

    public RestaurantViewModel() {
        restaurantCallbackListener = this;
    }

    public MutableLiveData<List<RestaurantModel>> getRestaurantList() {
        if (restaurantList == null){
            restaurantList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadRestaurant();
        }
        return restaurantList;
    }

    private void loadRestaurant() {
        List<RestaurantModel> tempList = new ArrayList<>();
        DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference(Common.RESTAURANT_REF);
        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot:dataSnapshot.getChildren()){

                    RestaurantModel restaurantModel = itemSnapshot.getValue(RestaurantModel.class);
                    tempList.add(restaurantModel);
                }
                restaurantCallbackListener.onRestaurantLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                restaurantCallbackListener.onRestaurantLoadFailed(databaseError.getMessage());

            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onRestaurantLoadSuccess(List<RestaurantModel> restaurantModels) {
        restaurantList.setValue(restaurantModels);

    }

    @Override
    public void onRestaurantLoadFailed(String message) {
        messageError.setValue(message);
    }
}
