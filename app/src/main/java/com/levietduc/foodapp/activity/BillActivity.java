package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.levietduc.foodapp.adapter.adapterBill;
import com.levietduc.foodapp.adapter.adapterCategory;
import com.levietduc.foodapp.databinding.ActivityBillBinding;
import com.levietduc.foodapp.model.modelBill;
import com.levietduc.foodapp.model.modelCategory;

public class BillActivity extends AppCompatActivity {
    ActivityBillBinding binding;
    adapterBill adapterBill;
    //Lấy Id người dùng
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bill);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewBill();
    }

    private void recyclerViewBill() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewBill.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<modelBill> options =
                new FirebaseRecyclerOptions.Builder<modelBill>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders").child(userId).child("-NYi52lz9IfUdKuTNhQk").child("Detail"),modelBill.class)
                        .build();

        adapterBill = new adapterBill(options);
        binding.recyclerViewBill.setAdapter(adapterBill);
    }
    protected void onStart() {
        super.onStart();
        adapterBill.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterBill.stopListening();
    }
}