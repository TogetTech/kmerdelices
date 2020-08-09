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
import com.togettech.kmerdelices.EventBus.BoulangerieItemClick;
import com.togettech.kmerdelices.EventBus.CategoryClick;
import com.togettech.kmerdelices.Model.BoulangerieModel;
import com.togettech.kmerdelices.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBoulangerieAdapter extends RecyclerView.Adapter<MyBoulangerieAdapter.MyViewHolder> {
    Context context;
    List<BoulangerieModel> boulangerieModelList;

    public MyBoulangerieAdapter(Context context, List<BoulangerieModel> boulangerieModelList) {
        this.context = context;
        this.boulangerieModelList = boulangerieModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_boulangerie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(boulangerieModelList.get(position).getImage())
                .into(holder.boulangerie_image);
        holder.boulangerie_name.setText(new StringBuilder(boulangerieModelList.get(position).getName()));

        //Event
        holder.setListener((view, pos) -> {
            Common.boulangerieSelected = boulangerieModelList.get(pos);
            EventBus.getDefault().postSticky(new BoulangerieItemClick(true, boulangerieModelList.get(pos)));
        });

    }

    @Override
    public int getItemCount() {
        return boulangerieModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;
        @BindView(R.id.img_boulangerie)
        ImageView boulangerie_image;
        @BindView(R.id.txt_boulangerie)
        TextView boulangerie_name;

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
        if (boulangerieModelList.size() == 1)
            return Common.DEFAULT_COLUMN_COUNT;
        else {
            if (boulangerieModelList.size() % 2 == 0)
                return Common.DEFAULT_COLUMN_COUNT;
            else
                return (position > 1 && position == boulangerieModelList.size() - 1) ? Common.FULL_WIDTH_COLUMN:Common.DEFAULT_COLUMN_COUNT;
        }
    }
}
