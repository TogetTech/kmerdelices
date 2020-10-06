package com.togettech.kmerdelices.ui.restaurant.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.togettech.kmerdelices.Adapter.MyFoodListAdapter;
import com.togettech.kmerdelices.Adapter.MyMenuRestaurantAdapter;
import com.togettech.kmerdelices.Adapter.MyRestaurantAdapter;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.Model.CommentModel;
import com.togettech.kmerdelices.Model.FoodModel;
import com.togettech.kmerdelices.Model.MenuRestaurantModel;
import com.togettech.kmerdelices.Model.RestaurantModel;
import com.togettech.kmerdelices.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;


public class RestaurantDetailFragment extends Fragment {

    private static final String TAG = "RestaurantDetailFragment";
    private FirebaseAuth firebaseAuth;

    private RestaurantDetailViewModel restaurantDetailViewModel;

    private Unbinder unbinder;
    private android.app.AlertDialog waitingDialog;

    @BindView(R.id.btn_rating)
    FloatingActionButton btn_rating;

    @BindView(R.id.img_restaurant)
    ImageView img_restaurant;
    @BindView(R.id.name_restaurant)
    TextView name_restaurant;
    @BindView(R.id.description_restaurant)
    TextView description_restaurant;
    @BindView(R.id.menu)
    TextView menu;

    @BindView(R.id.btn_wb_link)
    Button btn_wb_link;

    @BindView(R.id.ratingBar_restaurant)
    RatingBar ratingBar_restaurant;

    @BindView(R.id.recycler_menu_restaurant)
    RecyclerView recycler_menu_restaurant;

    LayoutAnimationController layoutAnimationController;

    @OnClick(R.id.btn_rating)
    void OnRatingButtonClick(){
        showDialogRating();

    }

    private void showDialogRating() {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Évaluation");

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_rating, null);

        RatingBar ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
        EditText edit_comment = (EditText) itemView.findViewById(R.id.edit_comment);

        builder.setView(itemView);

        builder.setNegativeButton("ANNULER", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            CommentModel commentModel = new CommentModel();
            assert firebaseUser != null;
            commentModel.setName(firebaseUser.getDisplayName());
            commentModel.setUid(firebaseUser.getUid());
            commentModel.setComment(edit_comment.getText().toString());
            commentModel.setRatingValue(ratingBar.getRating());
            Map<String, Object> serverTimeStamp = new HashMap<>();
            serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
            commentModel.setCommentTimeStamp(serverTimeStamp);

            restaurantDetailViewModel.setCommentModel(commentModel);
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }



    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restaurantDetailViewModel =
                ViewModelProviders.of(this).get(RestaurantDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews();
        restaurantDetailViewModel.getMutableLiveDataRestaurant().observe(this, restaurantModel -> {
            displayInfo(restaurantModel);
        });
        restaurantDetailViewModel.getModelMutableDataComment().observe(this, commentModel -> {
            submitRatingToFirebase(commentModel);
        });


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        return root;
    }

    private void displayInfo(RestaurantModel restaurantModel) {
        Glide.with(getContext()).load(restaurantModel.getImage()).into(img_restaurant);
        name_restaurant.setText(new StringBuilder(restaurantModel.getName()));
        description_restaurant.setText(new StringBuilder(restaurantModel.getDescription()));
        menu.setText(new StringBuilder(restaurantModel.getMenu()));

        btn_wb_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = restaurantModel.getWb_link().toString().trim();
                if (!url.startsWith("https://") && !url.startsWith("http://")){
                    url = "http://" + url;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        //Afficher les etoiles
        if (restaurantModel.getRatingValue() != null)
            ratingBar_restaurant.setRating(restaurantModel.getRatingValue().floatValue());


        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.restaurantSelected.getName());
    }

    private void initViews() {
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recycler_menu_restaurant.setHasFixedSize(true);
        recycler_menu_restaurant.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void submitRatingToFirebase(CommentModel commentModel) {
        waitingDialog.show();
        //First, we will submit to comment Ref
        FirebaseDatabase.getInstance()
                .getReference(Common.COMMENT_RESTAURANT_REF)
                .child(Common.restaurantSelected.getRestaurant_id())
                .setValue(commentModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        //After submit to CommentRef, we will update aveger in Food
                        addRatingToFood(commentModel.getRatingValue());
                    }
                    waitingDialog.dismiss();

                });
    }

    private void addRatingToFood(float ratingValue) {
        FirebaseDatabase.getInstance()
                .getReference(Common.RESTAURANT_REF)
                .child(Common.restaurantSelected.getRestaurant_id())
                .child("restaurants")
                .child(Common.restaurantSelected.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            RestaurantModel restaurantModel = dataSnapshot.getValue(RestaurantModel.class);
                            assert restaurantModel != null;
                            restaurantModel.setKey(Common.restaurantSelected.getKey());

                            //Apply rating
                            if (restaurantModel.getRatingValue() == null)
                                restaurantModel.setRatingValue(0d);
                            if (restaurantModel.getRatingCount() == null)
                                restaurantModel.setRatingCount(0L);
                            double sumRating = restaurantModel.getRatingValue()+ratingValue;
                            long ratingCount = restaurantModel.getRatingCount()+1;
                            double result = sumRating/ratingCount;

                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("ratingValue", result);
                            updateData.put("ratingCount", ratingCount);

                            //update data in variable
                            restaurantModel.setRatingValue(result);
                            restaurantModel.setRatingCount(ratingCount);

                            dataSnapshot.getRef()
                                    .updateChildren(updateData)
                                    .addOnCompleteListener(task -> {
                                        waitingDialog.dismiss();
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext(), "Merci !", Toast.LENGTH_SHORT).show();
                                            Common.restaurantSelected = restaurantModel;
                                            restaurantDetailViewModel.setRestaurantModel(restaurantModel);
                                        }

                                    });
                        }else
                            waitingDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        waitingDialog.dismiss();
                        Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}