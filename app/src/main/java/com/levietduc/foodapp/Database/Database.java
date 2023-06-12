package com.levietduc.foodapp.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.levietduc.foodapp.model.modelOrder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    public static final String DB_NAME = "zikiFood_db.db";
    public static final int DB_VER = 1;

    public Database (Context context){
        super(context, DB_NAME,null,DB_VER);
    }

    @SuppressLint("Range")
    public List<modelOrder> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"product_id","product_name","quantity","price"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<modelOrder> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new modelOrder(c.getString(c.getColumnIndex("product_id")),
                        c.getString(c.getColumnIndex("product_name")),
                        c.getString(c.getColumnIndex("quantity")),
                        c.getString(c.getColumnIndex("price"))
                ));
            }while (c.moveToNext());
        }
        return  result;
    }

    public void addToCart(modelOrder modelOrder){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(product_id,product_name,quantity,price) VALUES('%s','%s','%s','%s');",
                modelOrder.getProduct_id(),
                modelOrder.getProduct_name(),
                modelOrder.getQuantity(),
                modelOrder.getPrice());
        db.execSQL(query);
    }

    public void cleanCart(modelOrder modelOrder){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
