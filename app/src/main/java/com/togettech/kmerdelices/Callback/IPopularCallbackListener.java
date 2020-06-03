package com.togettech.kmerdelices.Callback;

import com.togettech.kmerdelices.Model.PopularCategoryModel;

import java.util.List;

public interface IPopularCallbackListener {

    void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels);
    void onPopularLLoadFailed(String message);
}
