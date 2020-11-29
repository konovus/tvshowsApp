package com.konovus.tvshowsapp.viewmodels;

import com.konovus.tvshowsapp.repositories.TVShowDetailsRepository;
import com.konovus.tvshowsapp.responses.TVShowDetailsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class TVShowDetailsViewModel extends ViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel(){
        tvShowDetailsRepository = new TVShowDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(int id){
        return tvShowDetailsRepository.getTVShowDetails(id);
    }
}
