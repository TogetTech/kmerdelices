package com.togettech.kmerdelices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.UserModel;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    private android.app.AlertDialog waitingDialog;
    private static final String TAG = "MainActivity";

    //Google Login
    private static final int RC_SIGN_IN = 234;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Button registration = findViewById(R.id.btn_register);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRegister();
            }
        });

        //GOOGLE LOGIN OPTIONS
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        RelativeLayout signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                // Launch Sign In
                signInGoogle();
            }
        });

        //Show Dialog
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(MainActivity.this).build();

    }

    private void signInGoogle() {
        //getting the google signin intent
        Intent signInIntent = googleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Utilisateur connecté", Toast.LENGTH_SHORT).show();
                            //updateUI();
                            showDialogRegister();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void showDialogRegister() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Inscrivez - vous");
        builder.setMessage("Vos coordonnées");

        View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_register, null);

        EditText edit_name = (EditText) itemView.findViewById(R.id.edit_name);
        EditText edit_address = (EditText) itemView.findViewById(R.id.edit_address);
        EditText edit_phone = (EditText) itemView.findViewById(R.id.edit_phone);
        EditText edit_password = (EditText) itemView.findViewById(R.id.edit_password);

        FirebaseUser userInfo = firebaseAuth.getCurrentUser();
        //edit_name.setText(userInfo.getDisplayName());
        //edit_address.setText(userInfo.getEmail());
        //edit_phone.setText(userInfo.getPhoneNumber());

        builder.setView(itemView);

        builder.setNegativeButton("ANNULER", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {

            UserModel userModel = new UserModel();
            userModel.setName(edit_name.getText().toString());
            userModel.setAddress(edit_address.getText().toString());
            userModel.setPhone(edit_phone.getText().toString());
            userModel.setPassword(edit_password.getText().toString());

            String txt_name = edit_name.getText().toString();
            String txt_address = edit_address.getText().toString();
            String txt_phone = edit_phone.getText().toString();
            String txt_password = edit_password.getText().toString();

            if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_address) ||
                    TextUtils.isEmpty(txt_phone)){

                Toast.makeText(MainActivity.this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 4){
                Toast.makeText(MainActivity.this, "Le mot de passe doit comporter au moins 4 caractères.", Toast.LENGTH_SHORT).show();
            } else {

                userRegister(txt_name, txt_address, txt_phone, txt_password);
            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void userRegister(final String name, final String address, final String phone, final  String password) {

        waitingDialog.show();
        waitingDialog.setCancelable(true);

        firebaseAuth.createUserWithEmailAndPassword(address, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String uid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCESER).child(uid);

                            HashMap<String, String > hashMap = new HashMap<>();
                            hashMap.put("uid", uid);
                            hashMap.put("name", name);
                            hashMap.put("address", address);
                            hashMap.put("phone", phone);
                            hashMap.put("password", password);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(1000);
                                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            //waitingDialog.dismiss();
                                        }).start();

                                    }
                                }
                            });

                        } else  {
                            Toast.makeText(MainActivity.this, "Impossible de vous inscire avec cet Email ou Mot de passe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI() {
        Toast.makeText(MainActivity.this, "Vous êtes connecté", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser.getDisplayName() !=null && currentUser.getEmail() != null) {
            updateUI();
        }
    }


 */
}
