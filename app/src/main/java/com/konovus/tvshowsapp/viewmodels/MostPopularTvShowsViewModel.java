package com.konovus.tvshowsapp.viewmodels;

import com.konovus.tvshowsapp.repositories.MostPopularTVShowsRepository;
import com.konovus.tvshowsapp.responses.TVShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MostPopularTvShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTvShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowsResponse> getMostPopularTvShows(int page){
        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }
}
