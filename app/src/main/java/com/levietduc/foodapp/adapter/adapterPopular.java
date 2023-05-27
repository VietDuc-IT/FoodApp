package com.levietduc.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.model.modelPopular;

import java.util.ArrayList;

public class adapterPopular extends RecyclerView.Adapter<adapterPopular.ViewHolder> {
    ArrayList<modelPopular> populars;

    public adapterPopular(ArrayList<modelPopular> populars) {
        this.populars = populars;
    }

    @NonNull
    @Override
    public adapterPopular.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPopular.ViewHolder holder, int position) {
        holder.title.setText(populars.get(position).getTitle());
        holder.price.setText(String.valueOf(populars.get(position).getPrice()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(populars.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.imgPopular);
    }

    @Override
    public int getItemCount() {
        return populars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,price;
        ImageView imgPopular;
        TextView btnAdd;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.txtPrice);
            imgPopular = itemView.findViewById(R.id.imgPopular);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
