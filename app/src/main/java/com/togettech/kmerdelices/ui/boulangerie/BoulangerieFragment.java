package com.togettech.kmerdelices.ui.boulangerie;

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

import com.togettech.kmerdelices.Adapter.MyBoulangerieAdapter;
import com.togettech.kmerdelices.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BoulangerieFragment extends Fragment {

    private BoulangerieViewModel boulangerieViewModel;

    Unbinder unbinder;

    @BindView(R.id.recycler_boulangerie)
    RecyclerView recycler_boulangerie;

    LayoutAnimationController layoutAnimationController;

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boulangerieViewModel =
                ViewModelProviders.of(this).get(BoulangerieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_boulangerie, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews();
        boulangerieViewModel.getBoulangerieList().observe(this, boulangerieModels ->  {
            MyBoulangerieAdapter adapter = new MyBoulangerieAdapter(getContext(), boulangerieModels);
            recycler_boulangerie.setAdapter(adapter);
            recycler_boulangerie.setLayoutAnimation(layoutAnimationController);
        });
        return root;
    }

    private void initViews() {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recycler_boulangerie.setHasFixedSize(true);
        recycler_boulangerie.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}