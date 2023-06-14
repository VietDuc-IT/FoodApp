package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterCategory;
import com.levietduc.foodapp.adapter.adapterPopular;
import com.levietduc.foodapp.adapter.adapterBanner;
import com.levietduc.foodapp.adapter.adapterProduct;
import com.levietduc.foodapp.databinding.ActivityMainBinding;
import com.levietduc.foodapp.model.modelBanner;
import com.levietduc.foodapp.model.modelCategory;
import com.levietduc.foodapp.model.modelPopular;
import com.levietduc.foodapp.model.modelProduct;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    adapterCategory adapterCategory;
    adapterProduct adapterProduct;
    adapterBanner adapterBanner;

    FirebaseDatabase database;
    DatabaseReference category,product;
    ArrayList<modelBanner> banners;

    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewPagerBanner();
        autoSlideImage();
        recyclerViewCategory();
        recyclerViewPopular();
        addEvents();
    }

    //========================================= BANNER =============================================
    private void viewPagerBanner() {
        banners = new ArrayList<>();
        banners.add(new modelBanner(R.drawable.banner));
        banners.add(new modelBanner(R.drawable.banner1));
        banners.add(new modelBanner(R.drawable.banner2));

        adapterBanner = new adapterBanner(this,banners);
        binding.viewPagerBanner.setAdapter(adapterBanner);
    }
    private void autoSlideImage() {
        if(banners == null || banners.isEmpty() || binding.viewPagerBanner == null){
            return;
        }

        if(timer == null){
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = binding.viewPagerBanner.getCurrentItem();
                        int totalItem = banners.size() - 1;
                        if (currentItem < totalItem){
                            currentItem++;
                            binding.viewPagerBanner.setCurrentItem(currentItem);
                        } else {
                            binding.viewPagerBanner.setCurrentItem(0);
                        }
                    }
                });
            }
        },1000,5000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
    //========================================= CATEGORY ============================================
    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        binding.viewCategory.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<modelCategory> options =
                new FirebaseRecyclerOptions.Builder<modelCategory>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("/foodApp/category"),modelCategory.class)
                        .build();

        adapterCategory = new adapterCategory(options);
        binding.viewCategory.setAdapter(adapterCategory);
    }

    //=========================================== POPULAR ===========================================
    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.viewPopular.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<modelProduct> options =
                new FirebaseRecyclerOptions.Builder<modelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("/foodApp/product"),modelProduct.class)
                        .build();

        adapterProduct = new adapterProduct(options) {
            @Override
            public int getItemViewType(int position) {
                return 1; // Chỉ định viewType là VIEW_TYPE_1 cho tất cả các item
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
                return new ViewHolder(view);
            }
        };
        binding.viewPopular.setAdapter(adapterProduct);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterCategory.startListening();
        adapterProduct.startListening();
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        adapterPopular.stopListening();
        adapterProduct.stopListening();
    }*/
    //=========================================== EVENTS ===========================================
    private void addEvents() {
        //=========================================== SEARCH ===========================================
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

                adapterProduct = new adapterProduct(options);
                adapterProduct.startListening();
                binding.viewPopular.setAdapter(adapterProduct);
            }
        });

        binding.txtShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });

        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
    }
}