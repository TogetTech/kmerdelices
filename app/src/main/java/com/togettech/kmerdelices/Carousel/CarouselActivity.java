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

import com.togettech.kmerdelices.R;



public class CarouselActivity extends AppCompatActivity {

    private ViewPager mySlideviewpage;
    private LinearLayout mDotlayout;
    private CarouselAdapter sliderAdapter;
    private int positionLastColoredDots = 0;

    private TextView[] mDots;



    private static final String TAG = "CarouselActivity";

    Button btn_next_login;

    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN_GO = 1001;
    GoogleSignInClient googleSignInClient;

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

        btn_next_login = findViewById(R.id.btn_next_login);
        btn_next_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInToGoogle();
            }
        });
        //Configure Google Client
        configureGoogleClient();

        //Initialize Firebase Auth
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();

    }

    //GOOGLE LOGIN .................................................................................
    private void configureGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private void signInToGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GO);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //Log.d(TAG, "signInWithCredential:success: currentUser: " + user.getEmail());
                            //showToastMessage("Firebase Authentication Succeeded ");
                            launchMainActivity(user);
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            showToastMessage("Firebase Authentication failed:" + task.getException());
                        }
                    }
                });
    }

    private void showToastMessage(String message) {
        Toast.makeText(CarouselActivity.this, message, Toast.LENGTH_LONG).show();
    }
    private void launchMainActivity(FirebaseUser user) {
        if (user != null) {
            //MainActivity.startActivity(this, user.getDisplayName());
            finish();
        }
    }

    //END GOOGLE LOGON..............................................................................


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

    //BACKGROUND RUN ...............................................................................
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN_GO) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //showToastMessage("Google Sign in Succeeded");
                firebaseAuthWithGoogle(account);
                updateUI();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                showToastMessage("Google Sign in Failed " + e);
            }
        }

    }
    private void updateUI() {
        //Toast.makeText(CarouselActivity.this, "Vous êtes connecté", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CarouselActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
           updateUI();
        }
    }
}
