package com.example.shlokpatel.travel2_mindforks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    ArrayList<ReviewInfo> arrayList;
    Context context;

    public ReviewAdapter(ArrayList<ReviewInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_item_layout, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ReviewInfo currentReview = arrayList.get(position);
        if (currentReview.getText().equals(""))
            position++;
        else {
            holder.relativeTime.setText(currentReview.getRelative_time_description());
            holder.reviewText.setText(currentReview.getText());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView reviewText, relativeTime;

        public ReviewHolder(View itemView) {
            super(itemView);
            reviewText = itemView.findViewById(R.id.reviewText);
            relativeTime = itemView.findViewById(R.id.relativeTime);
        }
    }
}
