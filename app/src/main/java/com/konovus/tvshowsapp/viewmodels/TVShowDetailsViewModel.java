package com.konovus.tvshowsapp.viewmodels;

import android.app.Application;

import com.konovus.tvshowsapp.database.TVShowsDatabase;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.repositories.TVShowDetailsRepository;
import com.konovus.tvshowsapp.responses.TVShowDetailsResponse;

import org.intellij.lang.annotations.Flow;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TVShowsDatabase tvShowsDatabase;


    public TVShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(int id){
        return tvShowDetailsRepository.getTVShowDetails(id);
    }

    public Completable addToWatchlist(TVShow tvShow){
        return tvShowsDatabase.tvShowDao().addToWatchlist(tvShow);
    }

    public Flowable<TVShow> getTvShowFromWatchlist(int tvShowId){
        return tvShowsDatabase.tvShowDao().getTvShowFromWatchlist(tvShowId);
    }

    public Completable removeTvShowFromWatchlist(TVShow tvShow){
        return tvShowsDatabase.tvShowDao().removeFromWatchlist(tvShow);
    }
}
