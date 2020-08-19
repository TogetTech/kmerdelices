package com.togettech.kmerdelices;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.togettech.kmerdelices.Database.CartDataSource;
import com.togettech.kmerdelices.Database.CartDatabase;
import com.togettech.kmerdelices.Database.LocalCartDataSource;
import com.togettech.kmerdelices.EventBus.BoulangerieDetailClick;
import com.togettech.kmerdelices.EventBus.BoulangerieItemClick;
import com.togettech.kmerdelices.EventBus.CategoryClick;
import com.togettech.kmerdelices.EventBus.CounterCartEvent;
import com.togettech.kmerdelices.EventBus.FoodItemClick;
import com.togettech.kmerdelices.EventBus.HideFabCart;
import com.togettech.kmerdelices.EventBus.RestaurantDetailClick;
import com.togettech.kmerdelices.EventBus.RestaurantItemClick;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;

    private CartDataSource cartDataSource;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onResume(){
        super.onResume();
        //counterCartItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu,
                R.id.nav_food_detail, R.id.nav_restaurant, R.id.nav_restaurant_detail,
                R.id.nav_boulangerie, R.id.nav_boulangerie_detail, R.id.nav_account, R.id.nav_licence)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawer.closeDrawers();
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                navController.navigate(R.id.nav_home);
                break;
            case R.id.nav_menu:
                navController.navigate(R.id.nav_menu);
                break;
            case R.id.nav_restaurant:
                navController.navigate(R.id.nav_restaurant);
                break;
            case R.id.nav_boulangerie:
                navController.navigate(R.id.nav_boulangerie);
                break;
            case R.id.nav_account:
                navController.navigate(R.id.nav_account);
                break;
            case R.id.nav_licence:
                navController.navigate(R.id.nav_licence);
                break;

        }
        return true;
    }

    //EventBus

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onCategorySelected(CategoryClick event){
        if (event.isSuccess()){
            navController.navigate(R.id.nav_food_list);
        }
    }

    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onFoodItemClick(FoodItemClick event){
        if (event.isSuccess()){
            navController.navigate(R.id.nav_food_detail);
        }
    }

    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onRestaurantItemClick(RestaurantItemClick event){
        if (event.isSuccess()){
            navController.navigate(R.id.nav_restaurant);
        }
    }
    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onRestaurantDetailClick(RestaurantDetailClick event){
        if (event.isSuccess()){
            navController.navigate(R.id.nav_restaurant_detail);
        }
    }

    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onBoulangerieItemClick(BoulangerieItemClick event){
        if (event.isSuccess()){
            navController.navigate(R.id.nav_boulangerie);
        }
    }

    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onBoulangerieDetailClick(BoulangerieDetailClick event){
        if (event.isSuccess()){
            navController.navigate(R.id.nav_boulangerie_detail);
        }
    }



    //**********************************************************************************************
    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onCartCounter(CounterCartEvent event){

        if (event.isSuccess()){

            //counterCartItem();

        }
    }

    @Subscribe(sticky = true, threadMode =  ThreadMode.MAIN)
    public void onHideFabEvent(HideFabCart event){

        if (event.isHidden()){
            //fab.hide();
        }else {
            //fab.show();
        }
    }
}
