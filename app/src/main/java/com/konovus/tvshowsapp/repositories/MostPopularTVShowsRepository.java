package com.konovus.tvshowsapp.repositories;

import com.konovus.tvshowsapp.network.ApiClient;
import com.konovus.tvshowsapp.network.ApiService;
import com.konovus.tvshowsapp.responses.TVShowsResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {

    private ApiService apiService;

    public MostPopularTVShowsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page){
        MutableLiveData<TVShowsResponse> tvShowsList = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowsResponse> call,@NonNull Response<TVShowsResponse> response) {
                tvShowsList.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowsResponse> call,@NonNull Throwable t) {
                tvShowsList.setValue(null);
            }
        });
        return tvShowsList;
    }
}
