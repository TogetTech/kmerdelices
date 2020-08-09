package com.togettech.kmerdelices.ui.boulangerie.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.BoulangerieModel;
import com.togettech.kmerdelices.R;
import com.togettech.kmerdelices.ui.restaurant.detail.RestaurantDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class BoulangerieDetailFragment extends Fragment {

    private BoulangerieDetailViewModel boulangerieDetailViewModel;

    private Unbinder unbinder;
    private android.app.AlertDialog waitingDialog;
    private BottomSheetDialog addonBottomSheetDialog;

    @BindView(R.id.img_boulangerie)
    ImageView img_boulangerie;
    @BindView(R.id.name_boulangerie)
    TextView name_boulangerie;
    @BindView(R.id.description_boulangerie)
    TextView description_boulangerie;

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boulangerieDetailViewModel =
                ViewModelProviders.of(this).get(BoulangerieDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_boulangerie_detail, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews();
        boulangerieDetailViewModel.getMutableLiveDataBoulangerie().observe(this, boulangerieModel -> {
            displayInfo(boulangerieModel);
        });
        return root;
    }

    private void displayInfo(BoulangerieModel boulangerieModel) {
        Glide.with(getContext()).load(boulangerieModel.getImage()).into(img_boulangerie);
        name_boulangerie.setText(new StringBuilder(boulangerieModel.getName()));
        description_boulangerie.setText(new StringBuilder(boulangerieModel.getDescription()));

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.boulangerieSelected.getName());

    }

    private void initViews() {
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
    }
}