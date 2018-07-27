package com.example.desided.news.internet;

import com.example.desided.news.data.NewsFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInteraction {

    @GET("everything?sources=abc-news")
    Call<NewsFeed> getFeed(@Query("apikey") String apikey);

}
