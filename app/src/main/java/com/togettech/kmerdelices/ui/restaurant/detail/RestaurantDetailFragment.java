package com.togettech.kmerdelices.ui.restaurant.detail;

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
import com.google.firebase.database.FirebaseDatabase;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.CommentModel;
import com.togettech.kmerdelices.Model.RestaurantModel;
import com.togettech.kmerdelices.R;
import com.togettech.kmerdelices.ui.fooddetail.FoodDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;


public class RestaurantDetailFragment extends Fragment {

    private RestaurantDetailViewModel restaurantDetailViewModel;

    private Unbinder unbinder;
    private android.app.AlertDialog waitingDialog;
    private BottomSheetDialog addonBottomSheetDialog;

    @BindView(R.id.img_restaurant)
    ImageView img_restaurant;
    @BindView(R.id.name_restaurant)
    TextView name_restaurant;
    @BindView(R.id.description_restaurant)
    TextView description_restaurant;

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restaurantDetailViewModel =
                ViewModelProviders.of(this).get(RestaurantDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews();
        restaurantDetailViewModel.getMutableLiveDataRestaurant().observe(this, restaurantModel -> {
            displayInfo(restaurantModel);
        });
        return root;
    }

    private void displayInfo(RestaurantModel restaurantModel) {
        Glide.with(getContext()).load(restaurantModel.getImage()).into(img_restaurant);
        name_restaurant.setText(new StringBuilder(restaurantModel.getName()));
        description_restaurant.setText(new StringBuilder(restaurantModel.getDescription()));

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.restaurantSelected.getName());
    }

    private void initViews() {
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
    }
}