package com.example.desided.news.adapter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desided.news.R;
import com.example.desided.news.data.NewsFeed;
import com.example.desided.news.db.AppDataBase;
import com.example.desided.news.db.ArticleDb;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ElementList extends AppCompatActivity{

    private static final String EXTRA_NEWS_FEED = "com.example.desided.news.adapter.ListAdapter.object";
    private ImageView mImageViewIcon;
    private TextView mTextViewTitle;
    private TextView mTextViewDescription;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_element);
        id = getIntent().getIntExtra(EXTRA_NEWS_FEED, 1);
        mTextViewDescription = findViewById(R.id.list_item_description);
        mTextViewTitle = findViewById(R.id.list_item_title);
        mImageViewIcon = findViewById(R.id.image_icon);
        setViews();

    }

    private void setViews(){
        AppDataBase appDataBase = AppDataBase.getINSTANCE(getApplication());
        ArticleDb articleDb = appDataBase.getArticleDao().findById(id);

        mTextViewTitle.setText(articleDb.getTitle());
        mTextViewDescription.setText(articleDb.getDescription());
        final Uri uri = Uri.parse(articleDb.getPicture());
        if(uri != null) {
            Picasso.get()
                    .load(uri)
                    .error(R.drawable.icons8_globe_48)
                    .into(mImageViewIcon, new Callback() {
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
