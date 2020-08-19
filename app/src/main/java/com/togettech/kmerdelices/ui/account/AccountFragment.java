package com.togettech.kmerdelices.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.togettech.kmerdelices.LaunchActivity;
import com.togettech.kmerdelices.LaunchActivity_ViewBinding;
import com.togettech.kmerdelices.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AccountFragment extends Fragment {

    Unbinder unbinder;

    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;


    @BindView(R.id.user_profil)
    ImageView user_profil;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;


    @BindView(R.id.btn_logout)
    Button btn_logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, root);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        Glide.with(getContext()).load(firebaseUser.getPhotoUrl()).into(user_profil);
        username.setText(firebaseUser.getDisplayName());
        email.setText(firebaseUser.getEmail());
        phone.setText(firebaseUser.getPhoneNumber());

        return root;


    }

    @OnClick(R.id.btn_logout)
    void OnLogoutButtonClick(){
        logout();
    }

    private void logout() {
        firebaseAuth.signOut();
        startActivity(new Intent(getContext(), LaunchActivity.class));
        getActivity().finish();
    }
}