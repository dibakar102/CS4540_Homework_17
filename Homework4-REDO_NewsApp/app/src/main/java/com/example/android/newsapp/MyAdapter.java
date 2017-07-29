package com.example.android.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.example.android.newsapp.data.Contract;

/**
 * Created by Dibakar on 7/28/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemHolder>{
    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;
    public static final String TAG = "myadapter";

    //constructor to accept cursor.
    public MyAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.itemlayout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        return new ItemHolder(view);
    }
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView newsTitle;
        public final TextView newsDescription;
        public final TextView newsTime;


        public final ImageView img;

        public ItemHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            newsDescription = (TextView) itemView.findViewById(R.id.newsDescription);
            newsTime = (TextView) itemView.findViewById(R.id.newsTime);
            img = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        public void bind(int pos) {
            cursor.moveToPosition(pos);

            newsTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_TITLE)));
            newsDescription.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_DESCRIPTION)));
            newsTime.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_PUBLISHED_AT)));

            //Picasso to load a thumbnail for each news item in recycler view
            
            String urlToImage = cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_URL_TO_IMAGE));
            Log.d(TAG, urlToImage);
            if(urlToImage != null){
                Picasso.with(context)
                        .load(urlToImage)
                        .into(img);
            }
        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(cursor, getAdapterPosition());
        }
    }
}
