package com.example.shlokpatel.travel2_mindforks;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends PagerAdapter {
    ArrayList<PicInfo> arrayList;
    Context context;

    public PhotoAdapter(ArrayList<PicInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                +arrayList.get(position).getPhoto_reference()+"&key="+Constants.API_KEY)
                .placeholder(R.drawable.loading)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
