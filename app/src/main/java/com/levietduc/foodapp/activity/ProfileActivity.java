package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.levietduc.foodapp.Helper.ManagmentCart;
import com.levietduc.foodapp.databinding.ActivityProfileBinding;
import com.levietduc.foodapp.model.modelProfile;
import com.levietduc.foodapp.model.modelUser;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    ManagmentCart managmentCart;

    //Lấy Id người dùng
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();

    private DatabaseReference mDatabase;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
        showProfile();
        showProfile2();
    }

    private void showProfile() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

        // Lắng nghe sự thay đổi dữ liệu
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Đọc dữ liệu từ dataSnapshot và cập nhật UI
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    binding.txtName.setText(name);
                    binding.txtEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProfile2() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child(userId);

        // Lắng nghe sự thay đổi dữ liệu
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Đọc dữ liệu từ dataSnapshot và cập nhật UI
                    String phone = dataSnapshot.child("phone").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);

                    binding.editTxtNumb.setText(phone);
                    binding.editTxtAddress.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        binding.btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAddress = binding.editTxtAddress.getText().toString();
                String userPhone = binding.editTxtNumb.getText().toString();
                writeNewUser(userId,userPhone,userAddress);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this,IntroActivity.class));
            }
        });
    }
    public void writeNewUser(String userId, String userPhone, String userAddress) {
        modelProfile profile = new modelProfile(userPhone,userAddress);

        database.getReference().child("Profile").child(userId).setValue(profile);
    }
}