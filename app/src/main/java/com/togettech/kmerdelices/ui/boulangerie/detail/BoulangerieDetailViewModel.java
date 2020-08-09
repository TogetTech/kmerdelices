package com.togettech.kmerdelices.ui.boulangerie.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.BoulangerieModel;
import com.togettech.kmerdelices.Model.CommentModel;

public class BoulangerieDetailViewModel extends ViewModel {
    private MutableLiveData<BoulangerieModel> mutableLiveDataBoulangerieList;
    private MutableLiveData<CommentModel> modelMutableDataComment;

    public void setCommentModel(CommentModel commentModel){
        if (modelMutableDataComment != null)
            modelMutableDataComment.setValue(commentModel);
    }

    public MutableLiveData<CommentModel> getModelMutableDataComment(){
        return modelMutableDataComment;
    }

    public BoulangerieDetailViewModel (){
        modelMutableDataComment = new MutableLiveData<>();
    }

    public MutableLiveData<BoulangerieModel> getMutableLiveDataBoulangerie() {
        if (mutableLiveDataBoulangerieList == null)
            mutableLiveDataBoulangerieList = new MutableLiveData<>();
        mutableLiveDataBoulangerieList.setValue(Common.boulangerieSelected);
        return mutableLiveDataBoulangerieList;
    }

    public void setBoulangerieModel(BoulangerieModel boulangerieModel) {
        if (mutableLiveDataBoulangerieList !=null)
            mutableLiveDataBoulangerieList.setValue(boulangerieModel);
    }
}
