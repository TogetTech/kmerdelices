package com.togettech.kmerdelices.Callback;


import com.togettech.kmerdelices.Model.BoulangerieModel;

import java.util.List;

public interface IBoulangerieCallbackListener {
    void onBoulangerieLoadSuccess(List<BoulangerieModel> boulangerieModelList);
    void onBoulangerieLoadFailed(String message);
}
