package com.levietduc.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.activity.DetailActivity;
import com.levietduc.foodapp.model.modelPopular;

import java.text.DecimalFormat;
import java.util.List;

public class adapterPopular extends RecyclerView.Adapter<adapterPopular.ViewHolder> {
    private Context context;
    private List<modelPopular> modelPopularList;

    public adapterPopular(Context context, List<modelPopular> modelPopularList) {
        this.context = context;
        this.modelPopularList = modelPopularList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.popularName.setText(modelPopularList.get(position).getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String price = decimalFormat.format(modelPopularList.get(position).getPrice());
        holder.popularPrice.setText(price);

        Glide.with(holder.popularImg.getContext()).load(modelPopularList.get(position).getImg()).into(holder.popularImg);
    }

    @Override
    public int getItemCount() {
        return modelPopularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView popularName,popularPrice;
        ImageView popularImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popularName = itemView.findViewById(R.id.txtTitleCart);
            popularImg = itemView.findViewById(R.id.imgCart);
            popularPrice = itemView.findViewById(R.id.txtPriceCart);
        }
    }
}
