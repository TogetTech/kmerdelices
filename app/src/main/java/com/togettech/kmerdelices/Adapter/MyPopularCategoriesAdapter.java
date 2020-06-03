package com.togettech.kmerdelices.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.togettech.kmerdelices.Model.PopularCategoryModel;
import com.togettech.kmerdelices.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPopularCategoriesAdapter extends RecyclerView.Adapter<MyPopularCategoriesAdapter.MyViewHolder> {

    Context context;
    List<PopularCategoryModel>popularCategoryModels;

    public MyPopularCategoriesAdapter(Context context, List<PopularCategoryModel> popularCategoryModels) {
        this.context = context;
        this.popularCategoryModels = popularCategoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_popular_categories_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(popularCategoryModels.get(position).getImage())
               .into(holder.category_image);

        holder.txt_category_name.setText(popularCategoryModels.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return popularCategoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Unbinder unbinder;

        @BindView(R.id.txt_category_name)
        TextView txt_category_name;
        @BindView(R.id.category_image)
        CircleImageView category_image;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }


    }
}
