package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.Database.Database;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.databinding.ActivityDetailBinding;
import com.levietduc.foodapp.model.modelOrder;
import com.levietduc.foodapp.model.modelProduct;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;

    String productId="";
    modelProduct modelProduct;
    private int numberOrder = 1;

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

        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                binding.txtNubItem.setText(""+numberOrder);
                binding.btnAddToCard.setText("Thêm vào giỏ hàng "+Math.round(numberOrder*getIntent().getDoubleExtra("dataPrice",0))+" VNĐ");
            }
        });

        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder == 0) {
                    Toast.makeText(DetailActivity.this,"Số lượng không nhỏ hơn 0",Toast.LENGTH_SHORT).show();
                }else{
                    numberOrder = numberOrder - 1;
                    binding.txtNubItem.setText(""+numberOrder);
                    binding.btnAddToCard.setText("Thêm vào giỏ hàng "+Math.round(numberOrder*getIntent().getDoubleExtra("dataPrice",0))+" VNĐ");
                }
            }
        });

        binding.btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new modelOrder(
                        productId,
                        modelProduct.getName(),
                        numberOrder.getNumber(),
                        modelProduct.getPrice()
                ));
            }
        });
    }
}