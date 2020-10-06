package com.togettech.kmerdelices.Carousel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.togettech.kmerdelices.HomeActivity;

import com.togettech.kmerdelices.LoginActivity;
import com.togettech.kmerdelices.R;



public class CarouselActivity extends AppCompatActivity {

    private ViewPager mySlideviewpage;
    private LinearLayout mDotlayout;
    private CarouselAdapter sliderAdapter;
    private int positionLastColoredDots = 0;

    private TextView[] mDots;




    Button btn_next_login;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        mAuth = FirebaseAuth.getInstance();

        //SLIDER
        mySlideviewpage = findViewById(R.id.slideviewpage);
        mDotlayout = findViewById(R.id.dotlayout);
        sliderAdapter = new CarouselAdapter(this);
        mySlideviewpage.setAdapter(sliderAdapter);

        initDots();

        addDotsIndicator(positionLastColoredDots, 0);

        mySlideviewpage.addOnPageChangeListener(viewListener);

        btn_next_login = findViewById(R.id.btn_next_login);
        btn_next_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarouselActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    //SLIDER........................................................................................
    private void initDots() {
        mDots = new TextView[4];
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.white));
            mDotlayout.addView(mDots[i]);
        }
    }

    public void addDotsIndicator(int lastDotPosotion, int position) {
        mDots[positionLastColoredDots].setTextColor(getResources().getColor(R.color.white));
        mDots[position].setTextColor(Color.BLUE);

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        positionLastColoredDots = position;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(positionLastColoredDots, i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    //END SLIDER....................................................................................


    private void updateUI() {
        //Toast.makeText(CarouselActivity.this, "Vous êtes connecté", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CarouselActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
    }
}