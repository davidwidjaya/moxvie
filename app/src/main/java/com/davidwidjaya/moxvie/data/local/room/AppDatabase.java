package com.davidwidjaya.moxvie.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.davidwidjaya.moxvie.data.local.entity.Movie;
import com.davidwidjaya.moxvie.data.local.entity.Tv;

@Database(entities = {Movie.class, Tv.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "App.db")
                        .build();
            }
        }
        return INSTANCE;
    }
}
