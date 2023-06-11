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
import com.levietduc.foodapp.model.modelProduct;

import java.text.DecimalFormat;

public class adapterProduct extends FirebaseRecyclerAdapter<modelProduct,adapterProduct.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapterProduct(@NonNull FirebaseRecyclerOptions<modelProduct> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull adapterProduct.ViewHolder holder, int position, @NonNull modelProduct model) {
        holder.productName.setText(model.getName());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        //holder.popularPrice.setText(decimalFormat.format(String.valueOf(model.getPrice())));
        holder.productPrice.setText(String.valueOf(model.getPrice()));

        Glide.with(holder.productImg.getContext()).load(model.getImg()).into(holder.productImg);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_product,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice;
        ImageView productImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txtTitleProduct);
            productPrice = itemView.findViewById(R.id.txtPriceProduct);
            productImg = itemView.findViewById(R.id.imgProduct);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Lấy dữ liệu từ Firebase Realtime Database cho item được click
                        modelProduct dataModel = getItem(position);

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
