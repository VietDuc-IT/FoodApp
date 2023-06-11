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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterCategory;
import com.levietduc.foodapp.adapter.adapterPopular;
import com.levietduc.foodapp.adapter.adapterBanner;
import com.levietduc.foodapp.databinding.ActivityMainBinding;
import com.levietduc.foodapp.model.modelBanner;
import com.levietduc.foodapp.model.modelCategory;
import com.levietduc.foodapp.model.modelPopular;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    adapterCategory adapterCategory;
    adapterPopular adapterPopular;
    adapterBanner adapterBanner;

    ArrayList<modelCategory> categories;
    ArrayList<modelPopular> populars;
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

        FirebaseRecyclerOptions<modelPopular> options =
                new FirebaseRecyclerOptions.Builder<modelPopular>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("/foodApp/product"),modelPopular.class)
                        .build();

        adapterPopular = new adapterPopular(options);
        binding.viewPopular.setAdapter(adapterPopular);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterCategory.startListening();
        adapterPopular.startListening();
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        adapterPopular.stopListening();
        adapterPopular.stopListening();
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
                FirebaseRecyclerOptions<modelPopular> options =
                        new FirebaseRecyclerOptions.Builder<modelPopular>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("/foodApp/product").orderByChild("name").startAt(toString).endAt(toString+"~"),modelPopular.class)
                                .build();

                adapterPopular = new adapterPopular(options);
                adapterPopular.startListening();
                binding.viewPopular.setAdapter(adapterPopular);
            }
        });

        binding.txtShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });
    }
}