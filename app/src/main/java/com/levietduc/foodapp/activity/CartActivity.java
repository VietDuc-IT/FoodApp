package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.levietduc.foodapp.Helper.ChangeNumberItemListener;
import com.levietduc.foodapp.Helper.ManagmentCart;
import com.levietduc.foodapp.adapter.adapterCart;
import com.levietduc.foodapp.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cart);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        initList();
        //calculateCart();
        setVariable();
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recycleViewList.setLayoutManager(linearLayoutManager);
        adapter = new adapterCart(managmentCart.getListCart(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });

        binding.recycleViewList.setAdapter(adapter);

        if (managmentCart.getListCart().isEmpty()){
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollView2.setVisibility(View.GONE);
        }else{
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollView2.setVisibility(View.VISIBLE);
        }
    }
    private void calculateCart(){
        double total = managmentCart.getTotail();
        binding.txtNumbPrice.setText(String.valueOf(total));
    }

}