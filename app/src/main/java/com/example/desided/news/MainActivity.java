package com.example.desided.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.desided.news.adapter.ElementList;
import com.example.desided.news.adapter.ListAdapter;
import com.example.desided.news.data.NewsFeed;
import com.example.desided.news.db.AppDataBase;
import com.example.desided.news.db.ArticleDb;
import com.example.desided.news.internet.ApiInteraction;
import com.example.desided.news.internet.InetClient;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private ApiInteraction mApiInteraction;
    private List<ArticleDb> mArticleDbList;
    private static final String tag = "TAG";
    private static final String EXTRA_NEWS_FEED = "com.example.desided.news.adapter.ListAdapter.object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        getData();
    }

    void getData(){

        InetClient inetClient = new InetClient();
        String apiKey = inetClient.apiKey;
        mApiInteraction = inetClient.getRetrofit().create(ApiInteraction.class);


        Call<NewsFeed> call = mApiInteraction.getFeed(apiKey);
        call.enqueue(new Callback<NewsFeed>() {
            @Override
            public void onResponse(Call<NewsFeed> call, Response<NewsFeed> response) {
                List<NewsFeed.Articles> articlesList = response.body().getArticles();
                mArticleDbList = initDb(articlesList);
                setViews(articlesList);
            }

            @Override
            public void onFailure(Call<NewsFeed> call, Throwable t) {

            }
        });
    }

    private List<ArticleDb> initDb(List<NewsFeed.Articles> articles){

        ArticleDb articleDb = new ArticleDb();
        for (NewsFeed.Articles article : articles){
            articleDb.setDate(article.publishedAt);
            articleDb.setDescription(article.description);
            articleDb.setPicture(article.urlToImage);
            articleDb.setTitle(article.title);
            AppDataBase.getINSTANCE(getApplication()).getArticleDao().insert(articleDb);
        }
        return AppDataBase.getINSTANCE(getApplication()).getArticleDao().getAll();
    }

    private void setViews(final List<NewsFeed.Articles> articlesList){

        mListAdapter = new ListAdapter(getApplication(), articlesList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListAdapter);


        mListAdapter.setListener(new ListAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getApplicationContext(), ElementList.class);
                intent.putExtra(EXTRA_NEWS_FEED, mArticleDbList.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
