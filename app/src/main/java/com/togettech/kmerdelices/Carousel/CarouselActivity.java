package com.togettech.kmerdelices.Carousel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.togettech.kmerdelices.HomeActivity;
import com.togettech.kmerdelices.MainActivity;
import com.togettech.kmerdelices.R;

import java.util.Collections;

public class CarouselActivity extends AppCompatActivity {

    private ViewPager mySlideviewpage;
    private LinearLayout mDotlayout;
    private CarouselAdapter sliderAdapter;
    private int positionLastColoredDots = 0;

    private TextView[] mDots;
    Button btn_next;

    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);


        // Initialize Firebase Auth
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();

        //SLIDER
        mySlideviewpage = findViewById(R.id.slideviewpage);
        mDotlayout = findViewById(R.id.dotlayout);
        sliderAdapter = new CarouselAdapter(this);
        mySlideviewpage.setAdapter(sliderAdapter);

        initDots();

        addDotsIndicator(positionLastColoredDots, 0);

        mySlideviewpage.addOnPageChangeListener(viewListener);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                //doPhoneLogin();
                Intent intent = new Intent(CarouselActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    //SLIDER
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

    //PHONE LOGIN

    private void doPhoneLogin() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                showAlertDialog(user);
            } else {
                Toast.makeText(getBaseContext(), "Phone Auth Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                CarouselActivity.this);

        // Set Title
        mAlertDialog.setTitle("Connexion réussie");

        // Set Message
        mAlertDialog.setMessage(" Le numéro de téléphone est " + user.getPhoneNumber());

        mAlertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(CarouselActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mAlertDialog.create();

        // Showing Alert Message
        mAlertDialog.show();
    }

    private void updateUI() {
        //Toast.makeText(CarouselActivity.this, "Vous êtes connecté", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CarouselActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //assert currentUser != null;
        if (currentUser != null) {
           updateUI();
        }
    }

 */


}
