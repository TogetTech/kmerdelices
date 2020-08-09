package com.togettech.kmerdelices.ui.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.togettech.kmerdelices.Adapter.MyRestaurantAdapter;
import com.togettech.kmerdelices.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RestaurantFragment extends Fragment {

    private RestaurantViewModel restaurantViewModel;

    Unbinder unbinder;

    @BindView(R.id.recycler_restaurant)
    RecyclerView recycler_restaurant;

    LayoutAnimationController layoutAnimationController;

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restaurantViewModel =
                ViewModelProviders.of(this).get(RestaurantViewModel.class);
        View root = inflater.inflate(R.layout.fragment_restaurant, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews();
        restaurantViewModel.getRestaurantList().observe(this, restaurantModels ->  {
            MyRestaurantAdapter adapter = new MyRestaurantAdapter(getContext(), restaurantModels);
            recycler_restaurant.setAdapter(adapter);
            recycler_restaurant.setLayoutAnimation(layoutAnimationController);
        });
        return root;
    }

    private void initViews() {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recycler_restaurant.setHasFixedSize(true);
        recycler_restaurant.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}