package com.example.desided.news.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ArticleDb.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public abstract ArticleDAO getArticleDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getINSTANCE(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
