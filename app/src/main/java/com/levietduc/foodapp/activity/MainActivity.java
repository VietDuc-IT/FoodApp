package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterCategory;
import com.levietduc.foodapp.adapter.adapterPopular;
import com.levietduc.foodapp.adapter.adapterBanner;
import com.levietduc.foodapp.databinding.ActivityMainBinding;
import com.levietduc.foodapp.model.modelBanner;
import com.levietduc.foodapp.model.modelCategory;
import com.levietduc.foodapp.model.modelPopular;

import java.util.ArrayList;
import java.util.List;
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
    }

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

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        binding.viewCategory.setLayoutManager(linearLayoutManager);

        categories = new ArrayList<>();
        categories.add(new modelCategory("Pizza", "cat_1"));
        categories.add(new modelCategory("Pizza", "cat_2"));
        categories.add(new modelCategory("Pizza", "cat_3"));
        categories.add(new modelCategory("Pizza", "cat_4"));
        categories.add(new modelCategory("Pizza", "cat_5"));

        adapterCategory = new adapterCategory(categories);
        binding.viewCategory.setAdapter(adapterCategory);
    }

    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.viewPopular.setLayoutManager(linearLayoutManager);

        populars = new ArrayList<>();
        populars.add(new modelPopular("Pizza","pop_1","abc",10.5,5));
        populars.add(new modelPopular("Pizza","pop_2","abc",10.5,5));
        populars.add(new modelPopular("Pizza","pop_3","abc",10.5,5));

        adapterPopular = new adapterPopular(populars);
        binding.viewPopular.setAdapter(adapterPopular);
    }
}