package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterPopular;
import com.levietduc.foodapp.adapter.adapterProduct;
import com.levietduc.foodapp.databinding.ActivityProductBinding;
import com.levietduc.foodapp.model.modelPopular;
import com.levietduc.foodapp.model.modelProduct;

public class ProductActivity extends AppCompatActivity {
    ActivityProductBinding binding;

    adapterProduct adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_product);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewPopular();
        searchProduct();
    }
    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.viewProduct.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<modelProduct> options =
                new FirebaseRecyclerOptions.Builder<modelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("/foodApp/product"),modelProduct.class)
                        .build();
        adapterProduct = new adapterProduct(options) {
            @Override
            public int getItemViewType(int position) {
                return 2; // Chỉ định viewType là VIEW_TYPE_1 cho tất cả các item
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_product, parent, false);
                return new ViewHolder(view);
            }
        };
        binding.viewProduct.setAdapter(adapterProduct);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterProduct.startListening();
    }

    private void searchProduct() {
        binding.editextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                goToSearchView(editable.toString());
            }

            private void goToSearchView(String toString) {
                FirebaseRecyclerOptions<modelProduct> options =
                        new FirebaseRecyclerOptions.Builder<modelProduct>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("/foodApp/product").orderByChild("name").startAt(toString).endAt(toString+"~"),modelProduct.class)
                                .build();

                adapterProduct = new adapterProduct(options) {
                    @Override
                    public int getItemViewType(int position) {
                        return 2; // Chỉ định viewType là VIEW_TYPE_1 cho tất cả các item
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_product, parent, false);
                        return new ViewHolder(view);
                    }
                };
                adapterProduct.startListening();
                binding.viewProduct.setAdapter(adapterProduct);
            }
        });
    }
}