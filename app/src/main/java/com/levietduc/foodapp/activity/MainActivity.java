package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterCategory;
import com.levietduc.foodapp.adapter.adapterPopular;
import com.levietduc.foodapp.databinding.ActivityMainBinding;
import com.levietduc.foodapp.model.modelCategory;
import com.levietduc.foodapp.model.modelPopular;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    RecyclerView.Adapter adapterCategory,adapterPopular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewCategory();
        recyclerViewPopular();
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        RecyclerView viewCategory;
        viewCategory = findViewById(R.id.viewCategory);
        viewCategory.setLayoutManager(linearLayoutManager);

        ArrayList<modelCategory> categories = new ArrayList<>();
        categories.add(new modelCategory("Pizza", "cat_1"));
        categories.add(new modelCategory("Pizza", "cat_2"));
        categories.add(new modelCategory("Pizza", "cat_3"));
        categories.add(new modelCategory("Pizza", "cat_4"));
        categories.add(new modelCategory("Pizza", "cat_5"));

        adapterCategory = new adapterCategory(categories);
        viewCategory.setAdapter(adapterCategory);
    }

    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView viewPopular;
        viewPopular = findViewById(R.id.viewPopular);
        viewPopular.setLayoutManager(linearLayoutManager);

        ArrayList<modelPopular> populars = new ArrayList<>();
        populars.add(new modelPopular("Pizza","pop_1","abc",10.5,5));
        populars.add(new modelPopular("Pizza","pop_2","abc",10.5,5));
        populars.add(new modelPopular("Pizza","pop_3","abc",10.5,5));

        adapterPopular = new adapterPopular(populars);
        viewPopular.setAdapter(adapterPopular);
    }
}