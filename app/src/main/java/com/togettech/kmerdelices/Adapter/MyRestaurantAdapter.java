package com.togettech.kmerdelices.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.togettech.kmerdelices.Callback.IRecyclerClickListener;
import com.togettech.kmerdelices.Common.Common;
import com.togettech.kmerdelices.EventBus.RestaurantDetailClick;
import com.togettech.kmerdelices.Model.RestaurantModel;
import com.togettech.kmerdelices.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyRestaurantAdapter extends RecyclerView.Adapter<MyRestaurantAdapter.MyViewHolder> {

    Context context;
    List<RestaurantModel> restaurantModelList;

    public MyRestaurantAdapter(Context context, List<RestaurantModel> restaurantModelList) {
        this.context = context;
        this.restaurantModelList = restaurantModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_restaurant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(restaurantModelList.get(position).getImage())
                .into(holder.restaurant_image);
        holder.restaurant_name.setText(new StringBuilder(restaurantModelList.get(position).getName()));
        holder.ville_restaurant.setText(new StringBuilder(restaurantModelList.get(position).getVille()));

        //Event
        holder.setListener((view, pos) -> {
            Common.restaurantSelected = restaurantModelList.get(pos);
            EventBus.getDefault().postSticky(new RestaurantDetailClick(true, restaurantModelList.get(pos)));
        });
    }

    @Override
    public int getItemCount() {
        return restaurantModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;
        @BindView(R.id.img_restaurant)
        ImageView restaurant_image;
        @BindView(R.id.txt_restaurant)
        TextView restaurant_name;
        @BindView(R.id.ville_restaurant)
        TextView ville_restaurant;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener){
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClickListener(view, getAdapterPosition());
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (restaurantModelList.size() == 1)
            return Common.DEFAULT_COLUMN_COUNT;
        else {
            if (restaurantModelList.size() % 2 == 0)
                return Common.DEFAULT_COLUMN_COUNT;
            else
                return (position > 1 && position == restaurantModelList.size() - 1) ? Common.FULL_WIDTH_COLUMN:Common.DEFAULT_COLUMN_COUNT;
        }
    }

}
