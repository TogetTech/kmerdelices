package com.togettech.kmerdelices;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.splash)
    ImageView splash;
    @BindView(R.id.slogan)
    TextView slogan;
    @BindView(R.id.togettech)
    TextView togettech;

    private static final int RC_SIGN_IN = 101;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        splash.startAnimation(animation);
        slogan.startAnimation(animation);
        togettech.startAnimation(animation);

        // Initialize Firebase Auth
        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        final Intent intent = new Intent(this, HomeActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                    //phoneAuthentification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

    private void phoneAuthentification() {
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
                showAlertDialog(user);
            } else {
                /**
                 *   Sign in failed. If response is null the user canceled the
                 *   sign-in flow using the back button. Otherwise check
                 *   response.getError().getErrorCode() and handle the error.
                 */
                Toast.makeText(getBaseContext(), "Phone Auth Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                LaunchActivity.this);

        // Set Title
        mAlertDialog.setTitle("Connexion réussie");

        // Set Message
        mAlertDialog.setMessage(" Le numéro de téléphone est " + user.getPhoneNumber());

        mAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mAlertDialog.create();

        // Showing Alert Message
        mAlertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            updateUI();
        }
    }

    private void updateUI() {
        Toast.makeText(LaunchActivity.this, "Vous êtes connecté", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
