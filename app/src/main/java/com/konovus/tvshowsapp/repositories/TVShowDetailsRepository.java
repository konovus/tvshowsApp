package com.konovus.tvshowsapp.repositories;

import com.konovus.tvshowsapp.network.ApiClient;
import com.konovus.tvshowsapp.network.ApiService;
import com.konovus.tvshowsapp.responses.TVShowDetailsResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private ApiService apiService;

    public TVShowDetailsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(int id){
        MutableLiveData<TVShowDetailsResponse> tvshowDetails = new MutableLiveData<>();
        apiService.getTVShowDetails(id).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailsResponse> call,@NonNull Response<TVShowDetailsResponse> response) {
                tvshowDetails.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailsResponse> call,@NonNull Throwable t) {
                tvshowDetails.setValue(null);
            }
        });
        return tvshowDetails;
    }
}
