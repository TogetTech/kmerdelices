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
import com.togettech.kmerdelices.Model.MenuRestaurantModel;
import com.togettech.kmerdelices.Model.RestaurantModel;
import com.togettech.kmerdelices.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyMenuRestaurantAdapter extends RecyclerView.Adapter<MyMenuRestaurantAdapter.MyViewHolder> {

    Context context;
    List<MenuRestaurantModel> menuRestaurantModelList;

    public MyMenuRestaurantAdapter(Context context, List<MenuRestaurantModel> menuRestaurantModelList) {
        this.context = context;
        this.menuRestaurantModelList = menuRestaurantModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_restaurant_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(menuRestaurantModelList.get(position).getMenu_restaurant_image())
                .into(holder.menu_restaurant_image);
        holder.menu_restaurant_name.setText(menuRestaurantModelList.get(position).getMenu_restaurant_name());
        holder.menu_restaurant_prix.setText(menuRestaurantModelList.get(position).getMenu_restaurant_prix());


    }

    @Override
    public int getItemCount() {
        return menuRestaurantModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Unbinder unbinder;

        @BindView(R.id.menu_restaurant_image)
        ImageView menu_restaurant_image;
        @BindView(R.id.menu_restaurant_name)
        TextView menu_restaurant_name;
        @BindView(R.id.menu_restaurant_prix)
        TextView menu_restaurant_prix;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            listener.onItemClickListener(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (menuRestaurantModelList.size() == 1)
            return Common.DEFAULT_COLUMN_COUNT;
        else{
            if (menuRestaurantModelList.size() %2 == 0)
                return Common.DEFAULT_COLUMN_COUNT;
            else
                return (position > 1 && position == menuRestaurantModelList.size() - 1) ? Common.FULL_WIDTH_COLUMN:Common.DEFAULT_COLUMN_COUNT;

        }
    }
}
