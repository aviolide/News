package com.example.desided.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desided.news.R;
import com.example.desided.news.data.NewsFeed;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NewsHolder>{

    private static final String EXTRA_NEWS_FEED = "com.example.desided.news.adapter.ListAdapter.object";
    private Context mContext;
    private List<NewsFeed.Articles> mArticles;
    private Listener mListener;

    public interface Listener{
        void onClick(int position);
    }

    public ListAdapter(Context context, List<NewsFeed.Articles> articles){
        this.mContext = context;
        this.mArticles = articles;
    }

    public void setListener(Listener listener){
        this.mListener = listener;
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewDate;
        ImageView mImageViewIcon;

        public NewsHolder(View itemView) {
            super(itemView);

            mImageViewIcon = itemView.findViewById(R.id.image_icon);
            mTextViewTitle = itemView.findViewById(R.id.title_main);
            mTextViewDate = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }

    }

    @Override
    public ListAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.element_list, parent, false);

        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.NewsHolder holder, int position) {
        NewsFeed.Articles articles = mArticles.get(position);
        holder.mTextViewDate.setText(articles.publishedAt);
        holder.mTextViewTitle.setText(articles.title);
        setupPicasso(holder, articles);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public void setupPicasso(NewsHolder holder, NewsFeed.Articles articles){

        if (articles.urlToImage == null){
            Picasso.get()
                    .load(R.drawable.icons8_globe_48)
                    .error(R.drawable.icons8_globe_48)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(holder.mImageViewIcon);
        } else {
            final Uri uri = Uri.parse(articles.urlToImage);
            Picasso.get()
                    .load(uri)
                    .into(holder.mImageViewIcon, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("image", uri.toString());
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
