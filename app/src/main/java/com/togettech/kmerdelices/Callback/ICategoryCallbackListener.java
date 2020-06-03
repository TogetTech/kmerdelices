package com.togettech.kmerdelices.Callback;

import com.togettech.kmerdelices.Model.CategoryModel;

import java.util.List;

public interface ICategoryCallbackListener {
    void onCategoryLoadSuccess(List<CategoryModel> categoryModelList);
    void onCategoryLoadFailed(String message);
}
