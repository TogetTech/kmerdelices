package com.togettech.kmerdelices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.UserModel;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private AlertDialog dialog;
    private DatabaseReference userRef;



    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop(){
        if (listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    UserRegitser();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();



    }

    private void UserRegitser() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Register");
        builder.setMessage("Please fill information");

        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_register, null);
        EditText edit_name = (EditText) itemView.findViewById(R.id.edit_name);
        EditText edit_address = (EditText) itemView.findViewById(R.id.edit_address);
        EditText edit_phone = (EditText) itemView.findViewById(R.id.edit_phone);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String uid = currentUser.getUid();

        edit_phone.setText(currentUser.getPhoneNumber().toString());
        builder.setView(itemView);

        builder.setNegativeButton("ANNULER", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("CONTINUER", (dialogInterface, i) -> {

            if (TextUtils.isEmpty(edit_name.getText().toString())){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(edit_address.getText().toString())){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            UserModel userModel = new UserModel();
            userModel.setUid(uid);
            userModel.setName(edit_name.getText().toString());
            userModel.setAddress(edit_address.getText().toString());
            userModel.setPhone(edit_phone.getText().toString());

            userRef.child(uid).setValue(userModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialogInterface.dismiss();
                                Toast.makeText(MainActivity.this, "Félicitation !", Toast.LENGTH_SHORT).show();
                                goToHomeActivity(userModel);
                            }
                        }
                    });



        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void goToHomeActivity(UserModel userModel) {
        Common.currentUser = userModel;
    }


}
