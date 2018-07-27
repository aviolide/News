package com.example.desided.news.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsFeed{

    @SerializedName("articles")
    private List<Articles> mArticles;

    public List<Articles> getArticles() {
        return mArticles;
    }

    public NewsFeed(Context context){

        if (mArticles == null){
            mArticles = new ArrayList<>();
        }
    }



    public class Articles{
        @SerializedName("title")
        public String title;
        @SerializedName("description")
        public String description;
        @SerializedName("url")
        public String url;
        @SerializedName("urlToImage")
        public String urlToImage;
        @SerializedName("publishedAt")
        public String publishedAt;

        public Articles(String title, String description, String url, String urlToImage, String publishedAt) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.urlToImage = urlToImage;
            this.publishedAt = publishedAt;
        }
    }

    public String getTitle(int i){
        return mArticles.get(i).title;
    }

    public String getDescription(int i){
        return mArticles.get(i).description;
    }

    public String getUrl(int i){
        return mArticles.get(i).url;
    }

    public String getUrlImage(int i){
        return mArticles.get(i).urlToImage;
    }
}
