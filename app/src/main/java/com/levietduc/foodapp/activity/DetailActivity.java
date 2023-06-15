package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.Helper.ManagmentCart;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.databinding.ActivityDetailBinding;
import com.levietduc.foodapp.model.modelOrder;
import com.levietduc.foodapp.model.modelPopular;
import com.levietduc.foodapp.model.modelProduct;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;

    String productId="";
    modelProduct modelProduct;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(DetailActivity.this);

        getBundle();
        addEvents();
    }

    private void getBundle() {
        modelProduct = (modelProduct) getIntent().getSerializableExtra("object");

        Glide.with(this)
                .load(modelProduct.getImg())
                .into(binding.imgDetail);

        binding.txtNameDetail.setText(modelProduct.getName());

        String price = decimalFormat.format(modelProduct.getPrice());
        binding.txtPriceDetail.setText(price);

        String addToCard = decimalFormat.format(Math.round(numberOrder*modelProduct.getPrice()));
        binding.btnAddToCard.setText("Thêm vào giỏ hàng "+addToCard+" VNĐ");
        //binding.btnAddToCard.setText("Thêm vào giỏ hàng "+Math.round(numberOrder*modelProduct.getPrice())+" VNĐ");
    }
    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        binding.btnPlusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                binding.txtNubItemCart.setText(String.valueOf(numberOrder));

                String addToCard = decimalFormat.format(Math.round(numberOrder*modelProduct.getPrice()));
                binding.btnAddToCard.setText("Thêm vào giỏ hàng "+addToCard+" VNĐ");
            }
        });
        binding.btnMinusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder == 0) {
                    Toast.makeText(DetailActivity.this,"Số lượng không nhỏ hơn 0",Toast.LENGTH_SHORT).show();
                }else{
                    numberOrder = numberOrder - 1;
                    binding.txtNubItemCart.setText(String.valueOf(numberOrder));

                    String addToCard = decimalFormat.format(Math.round(numberOrder*modelProduct.getPrice()));
                    binding.btnAddToCard.setText("Thêm vào giỏ hàng "+addToCard+" VNĐ");
                }
            }
        });

        binding.btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelProduct.setNumberInCart(numberOrder);
                managmentCart.insertProduct(modelProduct);
            }
        });
    }
}