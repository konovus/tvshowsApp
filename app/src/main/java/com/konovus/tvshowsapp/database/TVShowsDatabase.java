package com.konovus.tvshowsapp.database;

import android.content.Context;

import com.konovus.tvshowsapp.dao.TVShowDao;
import com.konovus.tvshowsapp.models.TVShow;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TVShow.class, version = 1, exportSchema = false)
public abstract class TVShowsDatabase extends RoomDatabase {

    private static TVShowsDatabase tvShowsDatabase;

    public static synchronized TVShowsDatabase getTvShowsDatabase(Context context){
        if(tvShowsDatabase == null)
            tvShowsDatabase = Room.databaseBuilder(context,
                    TVShowsDatabase.class, "tv_shows_db").build();
        return tvShowsDatabase;
    }

    public abstract TVShowDao tvShowDao();
}
