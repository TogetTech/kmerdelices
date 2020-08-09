package com.togettech.kmerdelices.EventBus;


import com.togettech.kmerdelices.Model.BoulangerieModel;

public class BoulangerieDetailClick {
    private boolean success;
    private BoulangerieModel boulangerieModel;

    public BoulangerieDetailClick(boolean success, BoulangerieModel boulangerieModel) {
        this.success = success;
        this.boulangerieModel = boulangerieModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BoulangerieModel getBoulangerieModel() {
        return boulangerieModel;
    }

    public void setBoulangerieModel(BoulangerieModel boulangerieModel) {
        this.boulangerieModel = boulangerieModel;
    }
}
