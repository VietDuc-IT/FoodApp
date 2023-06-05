package com.levietduc.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.model.modelCategory;

public class categoryAdapter extends FirebaseRecyclerAdapter<modelCategory,categoryAdapter.ViewHolder> {
    public categoryAdapter(@NonNull FirebaseRecyclerOptions<modelCategory> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull modelCategory model) {
        holder.categoryName.setText(model.getName());
        Glide.with(holder.categoryPic.getContext()).load(model.getImg()).into(holder.categoryPic);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.txtNameCategory);
            categoryPic = (ImageView) itemView.findViewById(R.id.imgCategory);
        }
    }
}
