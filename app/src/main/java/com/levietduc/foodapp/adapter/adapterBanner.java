package com.levietduc.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.levietduc.foodapp.R;
import com.levietduc.foodapp.model.modelBanner;

import java.util.List;

public class adapterBanner extends PagerAdapter {

    private Context context;
    private List<modelBanner> bannerList;

    public adapterBanner(Context context, List<modelBanner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_banner, container,false);
        ImageView imgPhoto = view.findViewById(R.id.imgBanner);

        modelBanner photo = bannerList.get(position);
        if(photo != null) {
            Glide.with(context).load(photo.getResourceId()).into(imgPhoto);
        }

        // add view to viewGroup
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(bannerList != null){
            return bannerList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //remove view
        container.removeView((View) object);
    }
}
