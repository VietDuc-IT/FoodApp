package com.levietduc.foodapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.activity.CategoryActivity;
import com.levietduc.foodapp.activity.DetailActivity;
import com.levietduc.foodapp.model.modelProduct;

import java.text.DecimalFormat;
import java.util.List;

import io.grpc.Context;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {
    CategoryActivity activity;
    List<modelProduct> list;

    public categoryAdapter(CategoryActivity activity, List<modelProduct> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.ViewHolder holder, int position) {
        holder.popularName.setText(list.get(position).getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String price = decimalFormat.format(list.get(position).getPrice());
        holder.popularPrice.setText(price);

        Glide.with(holder.popularImg.getContext()).load(list.get(position).getImg()).into(holder.popularImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("object", list.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
