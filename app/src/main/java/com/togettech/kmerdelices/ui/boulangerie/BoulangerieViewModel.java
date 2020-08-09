package com.togettech.kmerdelices.ui.boulangerie;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.togettech.kmerdelices.Callback.IBoulangerieCallbackListener;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.BoulangerieModel;
import com.togettech.kmerdelices.Model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class BoulangerieViewModel extends ViewModel implements IBoulangerieCallbackListener {

    private MutableLiveData<List<BoulangerieModel>> boulangerieList;
    private MutableLiveData<String> messageError;
    private IBoulangerieCallbackListener boulangerieCallbackListener;

    public BoulangerieViewModel() {
        boulangerieCallbackListener = this;
    }

    public MutableLiveData<List<BoulangerieModel>> getBoulangerieList() {
        if (boulangerieList == null){
            boulangerieList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadBoulangerie();
        }
        return boulangerieList;
    }

    private void loadBoulangerie() {
        List<BoulangerieModel> tempList = new ArrayList<>();
        DatabaseReference boulangerieRef = FirebaseDatabase.getInstance().getReference(Common.BOULANGERIE_REF);
        boulangerieRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot:dataSnapshot.getChildren()){

                    BoulangerieModel boulangerieModel = itemSnapshot.getValue(BoulangerieModel.class);
                    tempList.add(boulangerieModel);
                }
                boulangerieCallbackListener.onBoulangerieLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                boulangerieCallbackListener.onBoulangerieLoadFailed(databaseError.getMessage());

            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onBoulangerieLoadSuccess(List<BoulangerieModel> boulangerieModels) {
        boulangerieList.setValue(boulangerieModels);

    }

    @Override
    public void onBoulangerieLoadFailed(String message) {
        messageError.setValue(message);
    }
}
