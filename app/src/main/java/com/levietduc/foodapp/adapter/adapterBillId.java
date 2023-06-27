package com.levietduc.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.levietduc.foodapp.R;
import com.levietduc.foodapp.activity.BillActivity;
import com.levietduc.foodapp.model.modelBillId;

import java.util.List;

public class adapterBillId extends BaseAdapter {
    BillActivity activity;
    int itemLayout;
    List<modelBillId> products;

    // Constructor
    public adapterBillId(BillActivity activity, int itemLayout, List<modelBillId> products) {
        this.activity = activity;
        this.itemLayout = itemLayout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(itemLayout,null);

            //Binding views
            holder.txtProductInfo = view.findViewById(R.id.txtProductInfo);
            holder.imvEdit = view.findViewById(R.id.imvEdit);
            holder.imvDelete = view.findViewById(R.id.imvDelete);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        //Binding Data
        modelBillId p = products.get(i);
        holder.txtProductInfo.setText(p.getNodeId());

        holder.txtProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nodeId = p.getNodeId();
                activity.openDialog(nodeId);
            }
        });

        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nodeId = p.getNodeId();
                activity.openEditDialog(nodeId);
            }
        });

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nodeId = p.getNodeId();
                activity.openDeleteDialog(nodeId);
            }
        });

        return view;
    }

    public static class ViewHolder{
        TextView txtProductInfo;
        ImageView imvEdit,imvDelete;
    }
}

