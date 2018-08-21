package com.example.shlokpatel.travel2_mindforks;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Type;
import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    ArrayList<CityDesc> arrayList;
    Context context;

    public Adapter(ArrayList<CityDesc> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view=convertView;
        MyViewHolder myViewHolder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
            myViewHolder=new MyViewHolder(view);
            view.setTag(myViewHolder);
        }
        else
            myViewHolder= (MyViewHolder) view.getTag();

        CityDesc current=arrayList.get(position);
        myViewHolder.tName.setText(current.getName());
        myViewHolder.tAddress.setText(current.getFormatted_address());

//        myViewHolder.imageView.setImageResource(R.drawable.accept);
        Picasso.get().load(
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                        +current.getPhotos().get(0).getPhoto_reference()+"&key="+Constants.REVIEW_KEY)
                .error(R.drawable.image).placeholder(R.drawable.image)
                .into(myViewHolder.imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PlaceInfo.class);
                intent.putExtra("PLACEID",arrayList.get(position).getPlace_id());
                intent.putExtra("ADDRESS",arrayList.get(position).getFormatted_address());
                intent.putExtra("NAME",arrayList.get(position).getName());
                intent.putExtra("LATLNG",arrayList.get(position).getGeometry().getLocation());
                /*ActivityOptions activityOptions=ActivityOptions.makeCustomAnimation(context,R.anim.fadein,R.anim.fadein);
                ActivityCompat.startActivityForResult((Activity)PlaceInfo,intent,123,activityOptions.toBundle());*/
                context.startActivity(intent);

            }
        });
        return view;
    }
    private static class MyViewHolder{
        TextView tName;
        TextView tAddress;
        ImageView imageView;
        public MyViewHolder(final View view){
            tName=view.findViewById(R.id.name);
            tAddress=view.findViewById(R.id.address);
            imageView=view.findViewById(R.id.image);


        }
    }

}
