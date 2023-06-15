package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.levietduc.foodapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}