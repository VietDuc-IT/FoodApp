package com.levietduc.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.Helper.ChangeNumberItemListener;
import com.levietduc.foodapp.Helper.ManagmentCart;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.model.modelProduct;

import java.util.ArrayList;

public class adapterCart extends RecyclerView.Adapter<adapterCart.ViewHolder> {
    ArrayList<modelProduct> listProductSelection;
    private ManagmentCart managmentCart;
    ChangeNumberItemListener changeNumberItemListener;

    public adapterCart(ArrayList<modelProduct> listProductSelection, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.listProductSelection = listProductSelection;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listProductSelection.get(position).getName());
        holder.price.setText(String.valueOf(listProductSelection.get(position).getPrice()));
        holder.totalEachItem.setText((int) Math.round(listProductSelection.get(position).getNumberInCart()*listProductSelection.get(position).getPrice())+"VNĐ");
        holder.num.setText(String.valueOf(listProductSelection.get(position).getNumberInCart()));

        Glide.with(holder.itemView.getContext())
                .load(listProductSelection.get(position).getImg())
                .into(holder.pic);

        holder.plusItem.setOnClickListener(view -> managmentCart.plusNumberFood(listProductSelection, position, () -> {
            notifyDataSetChanged();
            changeNumberItemListener.changed();
        }));

        holder.minusItem.setOnClickListener(view -> managmentCart.minusNumberFood(listProductSelection, position, () -> {
            notifyDataSetChanged();
            changeNumberItemListener.changed();
        }));
    }

    @Override
    public int getItemCount() {
        return listProductSelection.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,plusItem,minusItem;
        ImageView pic;
        TextView totalEachItem,num,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitleCart);
            pic = itemView.findViewById(R.id.imgCart);
            plusItem = itemView.findViewById(R.id.btnPlusCart);
            minusItem = itemView.findViewById(R.id.btnMinusCart);
            totalEachItem = itemView.findViewById(R.id.txtTotalEachItem);
            num = itemView.findViewById(R.id.txtNubItemCart);
            price = itemView.findViewById(R.id.txtPriceCart);
        }
    }
}
