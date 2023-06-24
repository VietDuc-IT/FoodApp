package com.levietduc.foodapp.adapter;

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
import com.levietduc.foodapp.model.modelBill;

import java.text.DecimalFormat;

public class adapterBill extends FirebaseRecyclerAdapter<modelBill,adapterBill.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapterBill(@NonNull FirebaseRecyclerOptions<modelBill> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull modelBill model) {
        holder.Name.setText(model.getName());

        double price = Double.parseDouble(model.getPrice());
        double numb = Double.parseDouble(model.getNumber());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        double total = Math.round(price*numb);
        String formattedTotal = decimalFormat.format(total);
        String formattedPrice = decimalFormat.format(price);
        holder.Price.setText(formattedPrice+" x "+model.getNumber()+" = "+formattedTotal+" VNĐ");

        Glide.with(holder.Pic.getContext()).load(model.getImg()).into(holder.Pic);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_bill,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Price;
        ImageView Pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.txtName);
            Price = itemView.findViewById(R.id.txtPrice);
            Pic = itemView.findViewById(R.id.imageBill);
        }
    }
}
