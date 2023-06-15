package com.levietduc.foodapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.levietduc.foodapp.model.modelProduct;

import java.util.ArrayList;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertProduct(modelProduct item){
        ArrayList<modelProduct> listProduct = getListCart();
        boolean existAlready = false;
        int n = 0;
        for(int y=0;y<listProduct.size();y++){
            if(listProduct.get(y).getName().equals(item.getName())){
                existAlready = true;
                n=y;
                break;
            }
        }
        if(existAlready){
            listProduct.get(n).setNumberInCart(item.getNumberInCart());
        }else{
            listProduct.add(item);
        }
        tinyDB.putListObject("CartList",listProduct);
        Toast.makeText(context,"Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
    }

    public ArrayList<modelProduct> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberFood(ArrayList<modelProduct> listProduct,int position,ChangeNumberItemListener changeNumberItemListener){
        if(listProduct.get(position).getNumberInCart()==1){
            listProduct.remove(position);
        }else{
            listProduct.get(position).setNumberInCart(listProduct.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject("CartList",listProduct);
        changeNumberItemListener.changed();
    }
    public void plusNumberFood(ArrayList<modelProduct> listProduct,int position, ChangeNumberItemListener changeNumberItemListener){
        listProduct.get(position).setNumberInCart(listProduct.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList",listProduct);
        changeNumberItemListener.changed();
    }
    public Double getTotail(){
        ArrayList<modelProduct> listProduct2 = getListCart();
        double fee = 0;
        for(int i=0;i<listProduct2.size();i++){
            fee = fee+(listProduct2.get(i).getPrice()*listProduct2.get(i).getNumberInCart());
        }
        return fee;
    }
}
