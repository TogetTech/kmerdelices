package com.togettech.kmerdelices.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.togettech.kmerdelices.Adapter.MyCartAdapter;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Database.CartDataSource;
import com.togettech.kmerdelices.Database.CartDatabase;
import com.togettech.kmerdelices.Database.CartItem;
import com.togettech.kmerdelices.Database.LocalCartDataSource;
import com.togettech.kmerdelices.EventBus.HideFabCart;
import com.togettech.kmerdelices.EventBus.UpdateItemInCart;
import com.togettech.kmerdelices.R;
import com.togettech.kmerdelices.ui.cart.CartViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CartFragment extends Fragment {

    private Parcelable recyclerViewState;
    private CartDataSource cartDataSource;

    @BindView(R.id.recycler_cart)
    RecyclerView recycler_cart;
    @BindView(R.id.txt_food_price)
    TextView txt_food_price;
    @BindView(R.id.txt_empty_cart)
    TextView txt_empty_cart;
    @BindView(R.id.group_place_holder)
    CardView group_place_holder;

    private Unbinder unbinder;

    private CartViewModel cartViewModel;

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        cartViewModel.initCartDataSource(getContext());
        cartViewModel.getListMutableLiveDataCartItems().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
               if (cartItems == null || cartItems.isEmpty()){
                   recycler_cart.setVisibility(View.GONE);
                   group_place_holder.setVisibility(View.GONE);
                   txt_empty_cart.setVisibility(View.VISIBLE);

               }else {
                   recycler_cart.setVisibility(View.VISIBLE);
                   group_place_holder.setVisibility(View.VISIBLE);
                   txt_empty_cart.setVisibility(View.GONE);

                   MyCartAdapter adapter = new MyCartAdapter(getContext(), cartItems);
                   recycler_cart.setAdapter(adapter);
               }
            }
        });
        unbinder = ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(getContext()).cartDAO());

        EventBus.getDefault().postSticky(new HideFabCart(true));

        recycler_cart.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_cart.setLayoutManager(layoutManager);
        recycler_cart.addItemDecoration(new DividerItemDecoration(getContext(),layoutManager.getOrientation()));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().postSticky(new HideFabCart(false));
        cartViewModel.OnStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onUpdateItemInCartEvent(UpdateItemInCart event){
        if (event.getCartItem() != null){
            recyclerViewState = recycler_cart.getLayoutManager().onSaveInstanceState();
            cartDataSource.updateCartItems(event.getCartItem())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Integer>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Integer integer) {
                            calculateTotalPrice();
                            recycler_cart.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(), "[UPDATE CART]"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void calculateTotalPrice() {
        cartDataSource.sumPriceInCart(Common.currentUser.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Double>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Double price) {
                        txt_food_price.setText(new StringBuilder("Total: ")
                        .append(Common.formatPrice(price)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "[SUM CART]"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
