package com.konovus.tvshowsapp.viewmodels;

import com.konovus.tvshowsapp.repositories.SearchTVShowRepository;
import com.konovus.tvshowsapp.responses.TVShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel(){
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowsResponse> searchTvShow(String query, int page){
        return searchTVShowRepository.searchTVShow(query, page);
    }
}
