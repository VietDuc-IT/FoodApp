package com.levietduc.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.levietduc.foodapp.R;
import com.levietduc.foodapp.adapter.adapterCategory;
import com.levietduc.foodapp.adapter.adapterPopular;
import com.levietduc.foodapp.adapter.adapterBanner;
import com.levietduc.foodapp.databinding.ActivityMainBinding;
import com.levietduc.foodapp.model.modelBanner;
import com.levietduc.foodapp.model.modelCategory;
import com.levietduc.foodapp.model.modelPopular;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase db;

    public static final String DB_NAME = "zikiFood_db.db";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static final String TBL_NAME = "tb_category";
    ActivityMainBinding binding;
    adapterCategory adapterCategory;
    adapterPopular adapterPopular;
    adapterBanner adapterBanner;

    ArrayList<modelCategory> categories;
    ArrayList<modelPopular> populars;
    ArrayList<modelBanner> banners;

    ArrayAdapter<modelCategory> adapter;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        copyDB();
        openDB();

        viewPagerBanner();
        autoSlideImage();
        //recyclerViewCategory();
        recyclerViewPopular();
    }
    //=========================================== DATA ===========================================
    private void copyDB() {
        try{
            File dbFile = getDatabasePath(DB_NAME);
            if(!dbFile.exists()){
                if(processCopy()){
                    Toast.makeText(MainActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean processCopy() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX +
                DB_NAME;
        try {
            InputStream inputStream = getAssets().open(DB_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush(); outputStream.close(); inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openDB() {
        db = openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
    }
    //=========================================  =============================================
    private void loadDataFromDB() {
        categories = new ArrayList<>();
        modelCategory p;

        Cursor cursor = db.query(TBL_NAME,null,null,null,null,null,null);

        while (cursor.moveToNext()) {
            //int pId = cursor.getInt(0);
            String pName = cursor.getString(1);
            String pPoto = cursor.getString(2);
            //double pPrice = cursor.getDouble(2);
            p = new modelCategory(pName,pPoto);
            categories.add(p);
        }
        cursor.close();

        adapter = new ArrayAdapter<modelCategory>(this, android.R.layout.simple_list_item_1,categories);

        binding.viewCategory.setAdapter(adapterCategory);
    }

    @Override
    protected void onResume() {
        loadDataFromDB();
        super.onResume();
    }

    //========================================= BANNER =============================================
    private void viewPagerBanner() {
        banners = new ArrayList<>();
        banners.add(new modelBanner(R.drawable.banner));
        banners.add(new modelBanner(R.drawable.banner1));
        banners.add(new modelBanner(R.drawable.banner2));

        adapterBanner = new adapterBanner(this,banners);
        binding.viewPagerBanner.setAdapter(adapterBanner);
    }
    private void autoSlideImage() {
        if(banners == null || banners.isEmpty() || binding.viewPagerBanner == null){
            return;
        }

        if(timer == null){
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = binding.viewPagerBanner.getCurrentItem();
                        int totalItem = banners.size() - 1;
                        if (currentItem < totalItem){
                            currentItem++;
                            binding.viewPagerBanner.setCurrentItem(currentItem);
                        } else {
                            binding.viewPagerBanner.setCurrentItem(0);
                        }
                    }
                });
            }
        },1000,5000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
    //========================================= CATEGORY =============================================
    /*private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        binding.viewCategory.setLayoutManager(linearLayoutManager);

        categories = new ArrayList<>();
        categories.add(new modelCategory("Pizza", "cat_1"));
        categories.add(new modelCategory("Pizza", "cat_2"));
        categories.add(new modelCategory("Pizza", "cat_3"));
        categories.add(new modelCategory("Pizza", "cat_4"));
        categories.add(new modelCategory("Pizza", "cat_5"));

        adapterCategory = new adapterCategory(categories);
        binding.viewCategory.setAdapter(adapterCategory);
    }*/
    //=========================================== POPULAR ===========================================
    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.viewPopular.setLayoutManager(linearLayoutManager);

        populars = new ArrayList<>();
        populars.add(new modelPopular("Pizza","pop_1","abc",10.5,5));
        populars.add(new modelPopular("Pizza","pop_2","abc",10.5,5));
        populars.add(new modelPopular("Pizza","pop_3","abc",10.5,5));

        adapterPopular = new adapterPopular(populars);
        binding.viewPopular.setAdapter(adapterPopular);
    }
}