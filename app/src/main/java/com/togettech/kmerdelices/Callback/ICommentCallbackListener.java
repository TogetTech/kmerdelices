package com.togettech.kmerdelices.Callback;

import com.togettech.kmerdelices.Model.CommentModel;

import java.util.List;

public interface ICommentCallbackListener {
    void onCommentLoadSuccess(List<CommentModel> commentModels);
    void onCommentLoadFailed(String message);
}
