package com.example.desided.news.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticleDAO {

    @Insert
    void insert(ArticleDb articleDbs);

    @Query("SELECT * FROM article_db")
    List<ArticleDb> getAll();

    @Query("SELECT * FROM article_db WHERE ID LIKE :id")
    ArticleDb findById(int id);

}
