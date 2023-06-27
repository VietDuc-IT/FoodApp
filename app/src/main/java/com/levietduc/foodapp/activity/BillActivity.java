package com.levietduc.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterBill;
import com.levietduc.foodapp.adapter.adapterBillId;
import com.levietduc.foodapp.databinding.ActivityBillBinding;
import com.levietduc.foodapp.model.modelBill;
import com.levietduc.foodapp.model.modelBillId;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    ActivityBillBinding binding;
    adapterBillId adapter;
    adapterBill adapterBill;
    private DatabaseReference mDatabase;
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
        addEvents();
    }

    private void addEvents() {

    }

    private void recyclerViewBill() {
        DatabaseReference detailRef = FirebaseDatabase.getInstance().getReference().child("OrderDetail");
        detailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> nodeIdList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String nodeId = childSnapshot.getKey();
                    nodeIdList.add(nodeId);
                }

                List<modelBillId> itemList = new ArrayList<>();
                for (String nodeId : nodeIdList) {
                    itemList.add(new modelBillId(nodeId));
                }

                adapter = new adapterBillId(BillActivity.this,R.layout.item_list_bill,itemList);
                binding.listViewBill.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //================================== SHOW BILL DIALOG ==============================================
    public void openDialog(String p){
        Dialog dialog = new Dialog(BillActivity.this);
        dialog.setContentView(R.layout.dialog_show);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }

        RecyclerView recyclerView = dialog.findViewById(R.id.recycleViewDialog);
        TextView txtPrice = dialog.findViewById(R.id.txtPrice);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        // Danh sách sản phẩm
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<modelBill> options =
                new FirebaseRecyclerOptions.Builder<modelBill>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OrderDetail").child(p), modelBill.class)
                        .build();

        adapterBill = new adapterBill(options);
        recyclerView.setAdapter(adapterBill);
        adapterBill.startListening();

        // Tổng thanh toán
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId).child(p);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);

                    txtPrice.setText(price);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BillActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //================================== CancelBill DIALOG ==============================================
    public void openEditDialog(String p){
        Dialog dialog = new Dialog(BillActivity.this);
        dialog.setContentView(R.layout.dialog_edit);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }

        RecyclerView recyclerView = dialog.findViewById(R.id.recycleViewDialog);
        TextView txtPrice = dialog.findViewById(R.id.txtPrice);
        Button btnClose = dialog.findViewById(R.id.btnClose);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Danh sách sản phẩm
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<modelBill> options =
                new FirebaseRecyclerOptions.Builder<modelBill>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OrderDetail").child(p), modelBill.class)
                        .build();

        adapterBill = new adapterBill(options);
        recyclerView.setAdapter(adapterBill);
        adapterBill.startListening();

        // Tổng thanh toán
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId).child(p);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);

                    txtPrice.setText(price);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BillActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //================================== DeleteBill DIALOG ==============================================
    public void openDeleteDialog(String p){
        Dialog dialog = new Dialog(BillActivity.this);
        dialog.setContentView(R.layout.dialog_delete);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }

        RecyclerView recyclerView = dialog.findViewById(R.id.recycleViewDialog);
        TextView txtPrice = dialog.findViewById(R.id.txtPrice);
        Button btnClose = dialog.findViewById(R.id.btnClose);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);

        // Danh sách sản phẩm
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<modelBill> options =
                new FirebaseRecyclerOptions.Builder<modelBill>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OrderDetail").child(p), modelBill.class)
                        .build();

        adapterBill = new adapterBill(options);
        recyclerView.setAdapter(adapterBill);
        adapterBill.startListening();

        // Tổng thanh toán
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId).child(p);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);

                    txtPrice.setText(price);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BillActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}