package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.databinding.ActivityDetailBinding;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        addEvents();
    }

    private void getData() {
        binding.txtNameDetail.setText(getIntent().getStringExtra("dataName"));
        Glide.with(this)
                .load(getIntent().getStringExtra("dataImg"))
                .into(binding.imgDetail);

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        //binding.txtPriceDetail.setText(decimalFormat.format(String.valueOf(getIntent().getDoubleExtra("dataPrice",0))));
        binding.txtPriceDetail.setText(String.valueOf(getIntent().getDoubleExtra("dataPrice",0)));
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}