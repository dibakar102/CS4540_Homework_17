package com.example.android.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dibakar on 6/29/17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleAdapterViewHolder> {
    private static final String TAG = RecycleAdapter.class.getSimpleName();
    private ArrayList<NewsItem> data;
    final private ItemClickListener listener;
    public RecycleAdapter(ArrayList<NewsItem> data, ItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }
    public interface ItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    @Override
    public RecycleAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.itemlayout, viewGroup, shouldAttachToParentImmediately);

        return new RecycleAdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecycleAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class RecycleAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView newsTitle;
        TextView newsDescription;
        TextView newsTime;

        public RecycleAdapterViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            newsDescription = (TextView) view.findViewById(R.id.newsDescription);
            newsTime = (TextView) view.findViewById(R.id.newsTime);
            view.setOnClickListener(this);
        }

        public void bind(int pos) {
            NewsItem item = data.get(pos);
            newsTitle.setText(item.getTitle());
            newsDescription.setText(item.getDescription());
            newsTime.setText(item.getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            listener.onListItemClick(getAdapterPosition());

        }
    }

    public void setData(ArrayList<NewsItem> data) {
        this.data = data;
    }
}
