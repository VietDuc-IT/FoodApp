package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.levietduc.foodapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}