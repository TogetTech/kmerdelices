package com.togettech.kmerdelices.ui.fooddetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.CommentModel;
import com.togettech.kmerdelices.Model.FoodModel;


public class FoodDetailViewModel extends ViewModel {

    private MutableLiveData<FoodModel> mutableLiveDataFood;
    private MutableLiveData<CommentModel> modelMutableDataComment;

    public void setCommentModel(CommentModel commentModel){
        if (modelMutableDataComment != null)
            modelMutableDataComment.setValue(commentModel);
    }

    public MutableLiveData<CommentModel> getModelMutableDataComment(){
        return modelMutableDataComment;
    }

    public FoodDetailViewModel() {
        modelMutableDataComment = new MutableLiveData<>();
    }

    public MutableLiveData<FoodModel> getMutableLiveDataFood() {
        if (mutableLiveDataFood == null)
            mutableLiveDataFood = new MutableLiveData<>();
        mutableLiveDataFood.setValue(Common.selectedFood);
        return mutableLiveDataFood;
    }

    public void setFoodModel(FoodModel foodModel) {
        if (mutableLiveDataFood !=null)
                mutableLiveDataFood.setValue(foodModel);
    }
}