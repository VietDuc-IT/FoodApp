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

public class adapterPopular extends FirebaseRecyclerAdapter<modelPopular,adapterPopular.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapterPopular(@NonNull FirebaseRecyclerOptions<modelPopular> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull modelPopular model) {
        holder.popularName.setText(model.getName());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        //holder.popularPrice.setText(decimalFormat.format(String.valueOf(model.getPrice())));
        holder.popularPrice.setText(String.valueOf(model.getPrice()));

        Glide.with(holder.popularImg.getContext()).load(model.getImg()).into(holder.popularImg);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView popularName,popularPrice;
        ImageView popularImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popularName = itemView.findViewById(R.id.txtTitle);
            popularImg = itemView.findViewById(R.id.imgPopular);
            popularPrice = itemView.findViewById(R.id.txtPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Lấy dữ liệu từ Firebase Realtime Database cho item được click
                        modelPopular dataModel = getItem(position);

                        // Tạo Intent để chuyển sang Activity khác và truyền dữ liệu
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, DetailActivity.class);
                        // Truyền dữ liệu từ Firebase Realtime Database
                        intent.putExtra("dataName", dataModel.getName());
                        intent.putExtra("dataImg", dataModel.getImg());
                        intent.putExtra("dataPrice", dataModel.getPrice());

                        // Chạy Intent
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
