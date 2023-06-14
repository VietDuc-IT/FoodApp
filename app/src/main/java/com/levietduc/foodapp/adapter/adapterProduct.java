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
import com.levietduc.foodapp.model.modelProduct;

import java.text.DecimalFormat;

public class adapterProduct extends FirebaseRecyclerAdapter<modelProduct,adapterProduct.ViewHolder> {
    private static final int VIEW_TYPE_1 = 1;
    private static final int VIEW_TYPE_2 = 2;
    Context context;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("object", model);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        // Xác định loại layout tương ứng cho mỗi item
        if (position % 2 == 0) {
            return VIEW_TYPE_1;
        } else {
            return VIEW_TYPE_2;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_product, parent, false);
        }
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice;
        ImageView productImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txtTitleCart);
            productPrice = itemView.findViewById(R.id.txtPriceCart);
            productImg = itemView.findViewById(R.id.imgCart);
        }
    }
}
