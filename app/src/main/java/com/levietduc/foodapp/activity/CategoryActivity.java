package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterProduct;
import com.levietduc.foodapp.adapter.categoryAdapter;
import com.levietduc.foodapp.databinding.ActivityCartBinding;
import com.levietduc.foodapp.databinding.ActivityCategoryBinding;
import com.levietduc.foodapp.model.modelProduct;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    categoryAdapter adapterProduct;
    List<modelProduct> modelProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_category);

        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundle();
    }
    private void getBundle() {
        String type = getIntent().getStringExtra("type");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.viewProduct.setLayoutManager(linearLayoutManager);

        modelProductList = new ArrayList<>();
        adapterProduct = new categoryAdapter(this,modelProductList){
            @Override
            public int getItemViewType(int position) {
                return 2; // Chỉ định viewType là VIEW_TYPE_1 cho tất cả các item
            }

            @NonNull
            @Override
            public com.levietduc.foodapp.adapter.categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_product, parent, false);
                return new categoryAdapter.ViewHolder(view);
            }
        };
        binding.viewProduct.setAdapter(adapterProduct);

        if (type != null && type.equalsIgnoreCase("cháo")) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/foodApp/product");
            Query query = databaseReference.orderByChild("type").equalTo("cháo");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modelProductList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelProduct modelProduct = dataSnapshot.getValue(modelProduct.class);
                        modelProductList.add(modelProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("mì ý")) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/foodApp/product");
            Query query = databaseReference.orderByChild("type").equalTo("mì ý");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modelProductList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelProduct modelProduct = dataSnapshot.getValue(modelProduct.class);
                        modelProductList.add(modelProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("burger")) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/foodApp/product");
            Query query = databaseReference.orderByChild("type").equalTo("burger");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modelProductList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelProduct modelProduct = dataSnapshot.getValue(modelProduct.class);
                        modelProductList.add(modelProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("pizza")) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/foodApp/product");
            Query query = databaseReference.orderByChild("type").equalTo("pizza");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modelProductList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelProduct modelProduct = dataSnapshot.getValue(modelProduct.class);
                        modelProductList.add(modelProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("cơm")) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/foodApp/product");
            Query query = databaseReference.orderByChild("type").equalTo("cơm");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modelProductList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelProduct modelProduct = dataSnapshot.getValue(modelProduct.class);
                        modelProductList.add(modelProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("gà rán")) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/foodApp/product");
            Query query = databaseReference.orderByChild("type").equalTo("gà rán");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modelProductList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelProduct modelProduct = dataSnapshot.getValue(modelProduct.class);
                        modelProductList.add(modelProduct);
                    }
                    adapterProduct.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi xảy ra
                }
            });
        }
    }
}