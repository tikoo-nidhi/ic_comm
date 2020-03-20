package com.app.iccomm.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mImageIds = new ArrayList<>();
//    private int[] mImageIds = new int[]{R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};


    public ImageAdapter(Context mContext, ArrayList<String> mImageIds) {
        this.mContext = mContext;
        this.mImageIds = mImageIds;
    }

    @Override
    public int getCount() {
        return mImageIds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(mImageIds.get(position)).into(imageView);
//        imageView.setImageResource(mImageIds);
        container.addView(imageView, 0);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imageView);
        pAttacher.update();
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }


}
