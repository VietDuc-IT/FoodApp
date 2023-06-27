package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.levietduc.foodapp.Helper.ChangeNumberItemListener;
import com.levietduc.foodapp.Helper.ManagmentCart;
import com.levietduc.foodapp.adapter.adapterCart;
import com.levietduc.foodapp.databinding.ActivityCartBinding;
import com.levietduc.foodapp.model.modelBuyNow;
import com.levietduc.foodapp.model.modelProduct;
import com.levietduc.foodapp.model.modelProfile;
import com.levietduc.foodapp.model.modelUserOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    adapterCart adapterCart;
    RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String orderId = database.getReference().child("Orders").child(userId).push().getKey();
    //String orderId = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cart);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        initList();
        calculateCart();
        showProfile();
        showUser();
        addEvents();
    }
    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.editTxtName.getText().toString();
                String userAddress = binding.editTxtAddress.getText().toString();
                String userPhone = binding.edtTxtPhone.getText().toString();

                if(TextUtils.isEmpty(userAddress)){
                    Toast.makeText(CartActivity.this,"Bạn chưa nhập địa chỉ!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userPhone)){
                    Toast.makeText(CartActivity.this,"Bạn chưa nhập số điện thoại!",Toast.LENGTH_SHORT).show();
                    return;
                }

                List<modelProduct> cartList = managmentCart.getListCart();
                for (modelProduct cart : cartList) {
                    String itemName = cart.getName();
                    String itemPrice = String.valueOf(cart.getPrice());
                    String itemNub = String.valueOf(cart.getNumberInCart());
                    String itemImg = String.valueOf(cart.getImg());

                    writeNewOrder(itemName, itemNub, itemPrice, itemImg);
                }
                if(cartList.isEmpty()){
                    Toast.makeText(CartActivity.this,"Giỏ hàng trống, mua thêm!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    String totalPrice = String.valueOf(managmentCart.getTotail());
                    writeNewUser(userId, userName, userPhone, userAddress, totalPrice);

                    Toast.makeText(CartActivity.this,"Đặt hàng thành công!",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(5000);
                        startActivity(new Intent(CartActivity.this,MainActivity.class));
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    managmentCart.clear();
                }
            }
        });
    }

    public void writeNewUser(String userId, String userName, String userPhone, String userAddress, String price) {
        modelUserOrder userOrder = new modelUserOrder(userName, userPhone, userAddress, price);
        DatabaseReference userOrderRef = database.getReference().child("Orders").child(userId).child(orderId);
        userOrderRef.setValue(userOrder);
    }

    public void writeNewOrder(String itemName, String itemNub, String itemPrice, String itemImg) {
        modelBuyNow buyNow = new modelBuyNow(itemName, itemNub, itemPrice, itemImg);
        //DatabaseReference orderDetailRef = database.getReference().child("Orders").child(userId).child(orderId).child("Detail").push();
        DatabaseReference orderDetailRef = database.getReference().child("OrderDetail").child(orderId).push();
        orderDetailRef.setValue(buyNow);
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
        /*double percentTax = 1;
        double delivery = 1;
        tax = Math.round((managmentCart.getTotail()*percentTax*100.0))/100.0;
        double total = Math.round((managmentCart.getTotail()+tax+delivery)*100.0)/100;*/
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        double total = managmentCart.getTotail();
        binding.txtNumbPrice.setText(decimalFormat.format(total)+" VNĐ");
    }
    private void showProfile() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child(userId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String phone = dataSnapshot.child("phone").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);

                    binding.edtTxtPhone.setText(phone);
                    binding.editTxtAddress.setText(address);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CartActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showUser() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);

                    binding.editTxtName.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CartActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}