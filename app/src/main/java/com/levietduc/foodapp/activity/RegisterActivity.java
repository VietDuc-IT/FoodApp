package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.levietduc.foodapp.databinding.ActivityRegisterBinding;
import com.levietduc.foodapp.model.modelUser;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        binding.progressBar2.setVisibility(View.GONE);

        addEvents();
    }

    private void addEvents() {
        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
                binding.progressBar2.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createUser() {
        String userName = binding.editTxtName.getText().toString();
        String userEmail = binding.editTxtEmail.getText().toString();
        String userPassword = binding.editTxtPassword.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Bạn chưa nhập tên!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Bạn chưa nhập email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Bạn chưa nhập mật khẩu!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(this,"Mật khẩu phải lớn hơn 6 kí tự",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            modelUser modelUser = new modelUser(userName,userEmail,userPassword);
                            //Lấy Id người dùng
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("User").child(id).setValue(modelUser);
                            binding.progressBar2.setVisibility(View.GONE);

                            Toast.makeText(RegisterActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }else{
                            binding.progressBar2.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this,"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}