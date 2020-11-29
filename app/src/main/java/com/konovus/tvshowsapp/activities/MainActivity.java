package com.konovus.tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.konovus.tvshowsapp.R;
import com.konovus.tvshowsapp.adapters.TVShowsAdapter;
import com.konovus.tvshowsapp.databinding.ActivityMainBinding;
import com.konovus.tvshowsapp.models.TVShow;
import com.konovus.tvshowsapp.repositories.TVShowDetailsActivity;
import com.konovus.tvshowsapp.responses.TVShowsResponse;
import com.konovus.tvshowsapp.viewmodels.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsAdapter.TVShowListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTvShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter adapter;
    private int currentPage = 1;
    private int totalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.recyclerViewTvShows.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        adapter = new TVShowsAdapter(tvShows, this);
        activityMainBinding.recyclerViewTvShows.setAdapter(adapter);
        activityMainBinding.recyclerViewTvShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)
                        && currentPage < totalPages) {
                    currentPage++;
                    getMostPopular();
                }
            }
        });
        getMostPopular();
    }
    private void getMostPopular(){
        toggleLoading();
        viewModel.getMostPopularTvShows(currentPage).observe(this, tvShowsResponse -> {
            toggleLoading();
            if(tvShowsResponse != null && tvShowsResponse.getTvShows() != null){
                totalPages = tvShowsResponse.getPages();
                int oldCount = tvShows.size();
                tvShows.addAll(tvShowsResponse.getTvShows());
                adapter.notifyItemRangeInserted(oldCount, tvShows.size());
            }
        });
    }
    private void toggleLoading(){
        if(currentPage == 1){
            if(activityMainBinding.getIsLoading())
                activityMainBinding.setIsLoading(false);
            else
                activityMainBinding.setIsLoading(true);
        } else {
            if(activityMainBinding.getIsLoadingMore())
                activityMainBinding.setIsLoadingMore(false);
            else
                activityMainBinding.setIsLoadingMore(true);
        }
    }

    @Override
    public void OnTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(MainActivity.this, TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}
