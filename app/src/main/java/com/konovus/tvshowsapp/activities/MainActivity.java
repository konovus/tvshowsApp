package com.konovus.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.adapters.TVShowsAdapter;
import com.konovus.tvshowsapp.databinding.ActivityMainBinding;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.responses.TVShowsResponse;
import com.konovus.tvshowsapp.viewmodels.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTvShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.recyclerViewTvShows.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        adapter = new TVShowsAdapter(tvShows);
        activityMainBinding.recyclerViewTvShows.setAdapter(adapter);
        getMostPopular();
    }
    private void getMostPopular(){
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTvShows(0).observe(this, tvShowsResponse -> {
            activityMainBinding.setIsLoading(false);
            if(tvShowsResponse != null && tvShowsResponse.getTvShows() != null){
                tvShows.addAll(tvShowsResponse.getTvShows());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
